package com.software.dto.task;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class TaskDto {
    String title;
    String description;
    Integer complexity;

    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDateTime createTime;

    String taskStatus;
    String taskType;

    Long author;
    Long executor;
}
