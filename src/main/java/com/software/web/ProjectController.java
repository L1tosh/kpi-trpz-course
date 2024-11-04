package com.software.web;

import com.software.dto.project.ProjectDto;
import com.software.dto.project.ProjectEntry;
import com.software.dto.project.ProjectListDto;
import com.software.service.ProjectService;
import com.software.service.mapper.ProjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectMapper.toProjectDto(projectService.getProjectById(id)));
    }

    @GetMapping
    public ResponseEntity<ProjectListDto> getAllProjects() {
        return ResponseEntity.ok(projectMapper.toProjectListDto(projectService.getAllProjects()));
    }

    @PostMapping
    public ResponseEntity<ProjectEntry> createProject(@RequestBody @Valid ProjectDto projectToSave) {
        var savedProject = projectService.createProject(projectMapper.toProject(projectToSave));

        return ResponseEntity
                .created(URI.create("/api/v1/projects/%d".formatted(savedProject.getId())))
                .body(projectMapper.toProjectEntry(savedProject));
    }



}
