package com.software.service.mapper;

import com.software.common.TaskStatus;
import com.software.common.TaskType;
import com.software.domain.Task;
import com.software.dto.task.TaskDto;
import com.software.dto.task.TaskEntry;
import com.software.dto.task.TaskListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "complexity", target = "complexity")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "taskStatus", target = "taskStatus", qualifiedByName = "toTaskStatus")
    @Mapping(source = "taskType", target = "taskType", qualifiedByName = "toTaskType")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "executor", target = "executor")
    Task toTask(TaskDto taskDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "complexity", target = "complexity")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "taskStatus", target = "taskStatus", qualifiedByName = "toTaskStatus")
    @Mapping(source = "taskType", target = "taskType", qualifiedByName = "toTaskType")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "executor", target = "executor")
    Task toTask(TaskEntry taskEntry);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "complexity", target = "complexity")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "taskStatus", target = "taskStatus", qualifiedByName = "toTaskStatusDto")
    @Mapping(source = "taskType", target = "taskType", qualifiedByName = "toTaskTypeDto")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "executor", target = "executor")
    TaskDto toTaskDto(Task task);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "complexity", target = "complexity")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "taskStatus", target = "taskStatus", qualifiedByName = "toTaskStatusDto")
    @Mapping(source = "taskType", target = "taskType", qualifiedByName = "toTaskTypeDto")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "executor", target = "executor")
    TaskEntry toTaskEntry(Task task);

    List<TaskDto> toTaskDto(List<Task> taskList);
    List<Task> toTask(List<TaskDto> taskDtoList);

    @Named("toTaskStatus")
    default TaskStatus toTaskStatus(String taskStatus) {
        return taskStatus != null ? TaskStatus.valueOf(taskStatus) : null;
    }

    @Named("toTaskType")
    default TaskType toTaskType(String taskType) {
        return taskType != null ? TaskType.valueOf(taskType) : null;
    }

    @Named("toTaskStatusDto")
    default String toTaskStatusDto(TaskStatus taskStatus) {
        return taskStatus != null ? taskStatus.name() : null;
    }

    @Named("toTaskTypeDto")
    default String toTaskTypeDto(TaskType taskType) {
        return taskType != null ? taskType.name() : null;
    }

    @Named("toTaskListDto")
    default TaskListDto toTaskListDto(List<Task> taskList) {
        if (taskList == null) return null;

        return TaskListDto.builder().taskDtos(toTaskDto(taskList)).build();
    }

    @Named("toTaskList")
    default List<Task> toItemList(TaskListDto taskListDto) {
        return toTask(taskListDto.getTaskDtos());
    }

    void updateItem(Task source, @MappingTarget Task target);
}