package com.software.web.dto.methodology.kanban;

import com.software.web.dto.task.TaskListDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class KanbanColumnDto {
    Long id;
    String name;
    TaskListDto tasks;
}
