package com.software.dto.project;

import com.software.dto.event.EventListDto;
import com.software.dto.sprint.SprintListDto;
import com.software.dto.task.TaskListDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ProjectEntry {
    Long id;
    String name;
    String description;
    Long owner;
    List<Long> workers;
    EventListDto events;
    TaskListDto tasks;
    SprintListDto sprints;
}
