package com.software.service;

import com.software.domain.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long projectId);
    Project createProject(Project project);
    Project updateProject(Long projectId, Project updatedProject);
    void deleteProject(Long projectId);
    boolean isUserInProject(Long projectId, Long userId);
}
