package com.software.service.proxy;

import com.software.auth.jwt.JwtTokenUtil;
import com.software.domain.Project;
import com.software.service.ProjectService;
import com.software.service.exception.project.ProjectCreateException;
import com.software.service.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceProxy implements ProjectService {

    private final ProjectServiceImpl projectServiceImpl;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Cacheable("allProjects")
    public List<Project> getAllProjects() {
        log.info("Fetching all projects");
        List<Project> projects = projectServiceImpl.getAllProjects();
        log.debug("Fetched {} projects", projects.size());
        return projects;
    }

    @Override
    @Cacheable(value = "projects", key = "#projectId")
    public Project getProjectById(Long projectId) {
        log.info("Fetching project with id {}", projectId);
        Project project = projectServiceImpl.getProjectById(projectId);
        log.debug("Fetched project: {}", project);
        return project;
    }

    @Override
    @Transactional
    @CachePut(value = "projects", key = "#result.id")
    @CacheEvict(value = "allProjects", allEntries = true)
    public Project createProject(Project project) {
        try {
            log.info("Creating a new project with name {}", project.getName());
            Project createdProject = projectServiceImpl.createProject(project);
            log.info("Created project with id {}", createdProject.getId());
            return createdProject;
        } catch (ProjectCreateException e) {
            log.error("Error creating project with name {}: {}", project.getName(), e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    @CachePut(value = "projects", key = "#projectId")
    @CacheEvict(value = "allProjects", allEntries = true)
    public Project updateProject(Long projectId, Project updatedProject) {
        log.info("Updating project with id {}", projectId);
        validateUserIsOwner(projectId);
        Project project = projectServiceImpl.updateProject(projectId, updatedProject);
        log.info("Updated project with id {}", projectId);
        return project;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"allProjects", "projects"}, key = "#projectId", allEntries = true)
    public void deleteProject(Long projectId) {
        validateUserIsOwner(projectId);
        projectServiceImpl.deleteProject(projectId);
        log.info("Deleted project with id {}", projectId);
    }

    @Override
    public boolean isUserInProject(Long projectId, Long userId) {
        log.debug("Checking if user {} is part of project {}", userId, projectId);
        return projectServiceImpl.isUserInProject(projectId, userId);
    }

    private void validateUserIsOwner(Long projectId) {
        var userPrincipal = jwtTokenUtil.extractUserPrincipalFromContext();

        Project project = projectServiceImpl.getProjectById(projectId);
        if (!project.getOwner().equals(userPrincipal.getUserId())) {
            log.warn("User {} is not the owner of project {}", userPrincipal.getUserId(), projectId);
            throw new AccessDeniedException("User is not the owner of the project");
        }
    }
}
