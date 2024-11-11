package com.software.service.impl;

import com.software.data.ProjectRepository;
import com.software.domain.Project;
import com.software.service.ProjectService;
import com.software.service.UserService;
import com.software.service.exception.project.ProjectCreateException;
import com.software.service.exception.project.ProjectNotFoundException;
import com.software.service.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    private final UserService userService;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> {
            log.info("Project with id {} not found", projectId);
            return new ProjectNotFoundException(projectId);
        });
    }

    @Override
    @Transactional
    public Project createProject(Project project) {
        if (projectRepository.findByName(project.getName()).isPresent()) {
            log.info("Project with name {} already exist", project.getName());
            throw new ProjectCreateException(project.getName());
        }

        var owner = userService.getUserById(project.getOwner().getId());

        project.setOwner(owner);
        project.setWorkers(List.of(owner));

        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project updateProject(Long projectId, Project updatedProject) {
        var projectToUpdate = getProjectById(projectId);

        projectMapper.updateProject(updatedProject, projectToUpdate);

        return projectRepository.save(projectToUpdate);
    }

    @Override
    @Transactional
    public void deleteProject(Long projectId) {
        if (projectRepository.findById(projectId).isPresent()) {
            projectRepository.deleteById(projectId);

        } else {
            log.warn("Attempt to delete project with id {}", projectId);
        }
    }
}
