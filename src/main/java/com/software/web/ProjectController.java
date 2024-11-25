package com.software.web;

import com.software.annotation.CheckUserInProject;
import com.software.annotation.CheckUserIsOwner;
import com.software.web.dto.project.ProjectDto;
import com.software.web.dto.project.ProjectEntry;
import com.software.web.dto.project.ProjectListDto;
import com.software.service.ProjectService;
import com.software.service.mapper.ProjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@Validated
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectController(
            @Qualifier("projectServiceImpl") ProjectService projectService,
            ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable("id") Long projectId) {
        return ResponseEntity.ok(projectMapper.toProjectDto(projectService.getProjectById(projectId)));
    }

    @GetMapping
    public ResponseEntity<ProjectListDto> getAllProjects(@RequestParam Long userId) {
        if (userId != null)
            return ResponseEntity.ok(projectMapper.toProjectListDto(projectService.getAllUsersProjects(userId)));
        else
            return ResponseEntity.ok(projectMapper.toProjectListDto(projectService.getAllProjects()));
    }

    @PostMapping
    public ResponseEntity<ProjectEntry> createProject(@RequestBody @Valid ProjectDto projectToSave) {
        var savedProject = projectService.createProject(projectMapper.toProject(projectToSave));

        return ResponseEntity
                .created(URI.create("/api/v1/projects/%d".formatted(savedProject.getId())))
                .body(projectMapper.toProjectEntry(savedProject));
    }

    @CheckUserInProject
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @RequestBody @Valid ProjectDto updatedProjectDto) {
        var updatedProject =
                projectService.updateProject(id, projectMapper.toProject(updatedProjectDto));
        return ResponseEntity.ok(projectMapper.toProjectDto(updatedProject));
    }

    @CheckUserIsOwner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable("id") Long projectId) {
        projectService.deleteProject(projectId);

        return ResponseEntity.noContent().build();
    }

}
