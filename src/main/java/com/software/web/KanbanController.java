package com.software.web;

import com.software.domain.methodology.kanban.KanbanBoard;
import com.software.service.mapper.KanbanMapper;
import com.software.service.methodology.DevelopmentMethodology;
import com.software.service.methodology.factory.MethodologyServiceFactory;
import com.software.web.dto.methodology.kanban.KanbanBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project/{projectId}/kanban")
public class KanbanController {

    private final DevelopmentMethodology<KanbanBoard> methodologyService;
    private final KanbanMapper kanbanMapper;

    @Autowired
    public KanbanController(MethodologyServiceFactory factory, KanbanMapper kanbanMapper) {
        this.methodologyService = factory.getKanbanMethodologyService();
        this.kanbanMapper = kanbanMapper;
    }

    @GetMapping("/boards")
    public ResponseEntity<List<KanbanBoardDto>> getAllKanbanBoards(@PathVariable Long projectId) {
        var kanbanBoards = methodologyService.getAllArrangements(projectId);
        return ResponseEntity.ok(kanbanMapper.toKanbanBoardDto(kanbanBoards));
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<KanbanBoardDto> getKanbanBoardById(@PathVariable Long projectId, @PathVariable Long boardId) {
        var kanbanBoard = methodologyService.getArrangementById(projectId, boardId);
        return ResponseEntity.ok(kanbanMapper.toKanbanBoardDto(kanbanBoard));
    }

    @PostMapping("/boards")
    public ResponseEntity<KanbanBoardDto> createKanbanBoard(@PathVariable Long projectId, @RequestBody KanbanBoardDto kanbanBoardDto) {
        var savedBoard = methodologyService.save(projectId, kanbanMapper.toKanbanBoard(kanbanBoardDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(kanbanMapper.toKanbanBoardDto(savedBoard));
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<Void> deleteKanbanBoard(@PathVariable Long projectId, @PathVariable Long boardId) {
        methodologyService.deleteArrangement(projectId,boardId);
        return ResponseEntity.noContent().build();
    }
}
