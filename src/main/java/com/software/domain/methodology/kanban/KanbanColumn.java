package com.software.domain.methodology.kanban;

import com.software.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KanbanColumn {

    private Long id;
    private List<Task> tasks;
    private KanbanBoard kanbanBoard;
}
