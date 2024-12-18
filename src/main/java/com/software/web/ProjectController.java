package com.software.web;

import com.software.accesscontrol.annotation.CheckUserInProject;
import com.software.accesscontrol.annotation.CheckUserIsOwner;
import com.software.service.ProjectService;
import com.software.service.mapper.ProjectMapper;
import com.software.web.dto.project.ProjectDto;
import com.software.web.dto.project.ProjectEntry;
import com.software.web.dto.project.ProjectResources;
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
    public ResponseEntity<? extends ProjectResources> getProjectById(
            @PathVariable("id") Long projectId,
            @RequestParam(required = false, defaultValue = "false") Boolean fullInfo) {
        var searchingProject = projectService.getProjectById(projectId);

        if (Boolean.TRUE.equals(fullInfo))
            return ResponseEntity.ok(projectMapper.toProjectEntry(searchingProject));
        else
            return ResponseEntity.ok(projectMapper.toProjectDto(searchingProject));
    }


    @GetMapping
    public ResponseEntity<? extends ProjectResources> getAllProjects(
            @RequestParam(required = false, defaultValue = "0") Long userId) {
        if (userId != null)
            return ResponseEntity.ok(projectMapper.toProjectEntryList(projectService.getAllUsersProjects(userId)));
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
