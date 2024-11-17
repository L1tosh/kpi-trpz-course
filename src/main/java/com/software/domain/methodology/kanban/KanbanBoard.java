package com.software.domain.methodology.kanban;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KanbanBoard {

    private Long id;
    private String name;
    private List<KanbanColumn> columns;
}
