package com.software.dto.sprint;

import com.software.dto.task.TaskListDto;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class SprintDto {
    LocalDateTime startTime;
    LocalDateTime endTime;
    List<Long> command;
    TaskListDto tasks;
}
