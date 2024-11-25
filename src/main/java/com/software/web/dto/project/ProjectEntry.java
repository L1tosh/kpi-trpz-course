package com.software.web.dto.project;

import com.software.web.dto.event.EventListDto;
import com.software.web.dto.sprint.SprintListDto;
import com.software.web.dto.task.TaskListDto;
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