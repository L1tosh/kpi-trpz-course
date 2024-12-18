package com.software.web;

import com.software.accesscontrol.annotation.CheckUserInProject;
import com.software.domain.methodology.scrum.Sprint;
import com.software.service.bridge.DevelopmentMethodology;
import com.software.service.bridge.MethodologyServiceFactory;
import com.software.service.mapper.SprintMapper;
import com.software.web.dto.methodology.scrum.SprintDto;
import com.software.web.dto.methodology.scrum.SprintListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project/{projectId}/scrum")
public class ScrumController {
    private final DevelopmentMethodology<Sprint> methodologyService;
    private final SprintMapper sprintMapper;

    @Autowired
    public ScrumController(MethodologyServiceFactory factory, SprintMapper sprintMapper) {
        this.methodologyService = factory.getScrumMethodologyService();
        this.sprintMapper = sprintMapper;
    }

    @CheckUserInProject
    @GetMapping("/sprints")
    public ResponseEntity<SprintListDto> getAllSprints(@PathVariable Long projectId) {
        var sprints = methodologyService.getAllArrangements(projectId);

        return ResponseEntity.ok(sprintMapper.toSprintListDto(sprints));
    }

    @CheckUserInProject
    @GetMapping("/sprints/{sprintId}")
    public ResponseEntity<SprintDto> getSprintById(@PathVariable Long projectId, @PathVariable Long sprintId) {
        var sprint = methodologyService.getArrangementById(projectId, sprintId);

        return ResponseEntity.ok(sprintMapper.toSprintDto(sprint));
    }

    @CheckUserInProject
    @PostMapping("/sprints")
    public ResponseEntity<SprintDto> createSprint(@PathVariable Long projectId, @RequestBody SprintDto sprintDto) {
        var savedSprint = methodologyService.save(projectId, sprintMapper.toSprint(sprintDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(sprintMapper.toSprintDto(savedSprint));
    }

    @CheckUserInProject
    @DeleteMapping("/sprints/{sprintId}")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long projectId, @PathVariable Long sprintId) {
        methodologyService.deleteArrangement(projectId, sprintId);
        return ResponseEntity.noContent().build();
    }


}
