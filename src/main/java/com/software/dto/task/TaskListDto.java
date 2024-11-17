package com.software.dto.task;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class TaskListDto {
    List<TaskDto> taskDtos;
}
