package com.software.service.mapper;

import com.software.domain.methodology.kanban.KanbanBoard;
import com.software.domain.methodology.kanban.KanbanColumn;
import com.software.web.dto.methodology.kanban.KanbanBoardDto;
import com.software.web.dto.methodology.kanban.KanbanColumnDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ProjectMapper.class, TaskMapper.class})
public interface KanbanMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "columns", target = "columns")
    @Mapping(source = "project", target = "project", qualifiedByName = "toProject")
    KanbanBoard toKanbanBoard(KanbanBoardDto kanbanBoardDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "columns", target = "columns")
    @Mapping(source = "project", target = "project", qualifiedByName = "toProjectDto")
    KanbanBoardDto toKanbanBoardDto(KanbanBoard kanbanBoard);

    List<KanbanBoard> toKanbanBoard(List<KanbanBoardDto> kanbanBoardDtos);

    List<KanbanBoardDto> toKanbanBoardDto(List<KanbanBoard> kanbanBoards);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "tasks", target = "tasks", qualifiedByName = "toTaskList")
    KanbanColumn toKanbanColumn(KanbanColumnDto kanbanColumnDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "tasks", target = "tasks", qualifiedByName = "toTaskListDto")
    KanbanColumnDto toKanbanColumnDto(KanbanColumn kanbanColumn);

    List<KanbanColumn> toKanbanColumn(List<KanbanColumnDto> kanbanColumnDtos);

    List<KanbanColumnDto> toKanbanColumnDto(List<KanbanColumn> kanbanColumns);

    @Named("toKanbanBoardList")
    default List<KanbanBoard> toKanbanBoardList(List<KanbanBoardDto> kanbanBoardDtos) {
        return toKanbanBoard(kanbanBoardDtos);
    }

    @Named("toKanbanBoardListDto")
    default List<KanbanBoardDto> toKanbanBoardListDto(List<KanbanBoard> kanbanBoards) {
        if (kanbanBoards == null) return Collections.emptyList();
        return toKanbanBoardDto(kanbanBoards);
    }
}