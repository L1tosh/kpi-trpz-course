package com.software.web.dto.methodology.kanban;

import com.software.web.dto.project.ProjectDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class KanbanBoardDto {
    Long id;
    String name;
    List<KanbanColumnDto> columns;
    ProjectDto project;
}
