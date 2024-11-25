package com.software.service.proxy;

import com.software.auth.jwt.JwtTokenUtil;
import com.software.domain.Project;
import com.software.domain.user.Role;
import com.software.service.ProjectService;
import com.software.service.UserService;
import com.software.service.exception.project.ProjectCreateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProjectServiceProxy implements ProjectService {

    private final ProjectService projectService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ProjectServiceProxy(
            @Qualifier("projectServiceImpl") ProjectService projectService,
            @Qualifier("userServiceImpl") UserService userService,
            JwtTokenUtil jwtTokenUtil) {
        this.projectService = projectService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    @Cacheable("allProjects")
    public List<Project> getAllProjects() {
        log.info("Fetching all projects");
        var projects = projectService.getAllProjects();
        log.debug("Fetched {} projects", projects.size());
        return projects;
    }

    @Override
    public List<Project> getAllUsersProjects(Long userId) {
        log.info("Fetching all users projects by user id {}", userId);
        return projectService.getAllUsersProjects(userId);
    }

    @Override
    @Cacheable(value = "projects", key = "#projectId")
    public Project getProjectById(Long projectId) {
        log.info("Fetching project with id {}", projectId);
        var project = projectService.getProjectById(projectId);
        log.debug("Fetched project: {}", project);
        return project;
    }

    @Override
    @CachePut(value = "projects", key = "#result.id")
    @CacheEvict(value = "allProjects", allEntries = true)
    public Project createProject(Project project) {
        try {
            log.info("Creating a new project with name {}", project.getName());
            var createdProject = projectService.createProject(project);
            log.info("Created project with id {}", createdProject.getId());

            log.info("Adding role to project owner with id {}", createdProject.getOwner());
            userService.addRoleToUser(createdProject.getId(), createdProject.getOwner(), Role.builder().name("Owner").build());
            log.info("Added role to project owner with id {}", createdProject.getOwner());

            return createdProject;
        } catch (ProjectCreateException e) {
            log.error("Error creating project with name {}: {}", project.getName(), e.getMessage());
            throw e;
        }
    }

    @Override
    @CachePut(value = "projects", key = "#projectId")
    @CacheEvict(value = "allProjects", allEntries = true)
    public Project updateProject(Long projectId, Project updatedProject) {
        log.info("Updating project with id {}", projectId);
        validateUserIsOwner(projectId);
        var project = projectService.updateProject(projectId, updatedProject);
        log.info("Updated project with id {}", projectId);
        return project;
    }

    @Override
    @CacheEvict(value = {"allProjects", "projects"}, key = "#projectId", allEntries = true)
    public void deleteProject(Long projectId) {
        validateUserIsOwner(projectId);
        projectService.deleteProject(projectId);
        log.info("Deleted project with id {}", projectId);
    }

    @Override
    public boolean isUserInProject(Long projectId, Long userId) {
        log.debug("Checking if user {} is part of project {}", userId, projectId);
        return projectService.isUserInProject(projectId, userId);
    }

    private void validateUserIsOwner(Long projectId) {
        var userPrincipal = jwtTokenUtil.extractUserPrincipalFromContext();

        var project = projectService.getProjectById(projectId);
        if (!project.getOwner().equals(userPrincipal.getUserId())) {
            log.warn("User {} is not the owner of project {}", userPrincipal.getUserId(), projectId);
            throw new AccessDeniedException("User is not the owner of the project");
        }
    }
}
