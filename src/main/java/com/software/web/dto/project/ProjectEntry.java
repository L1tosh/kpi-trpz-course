package com.software.web.dto.project;

import com.software.web.dto.event.EventListDto;
import com.software.web.dto.methodology.scrum.SprintListDto;
import com.software.web.dto.task.TaskListDto;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ProjectEntry implements ProjectResources {
    Long id;
    String name;
    String description;
    Long owner;
    List<Long> workers;
    EventListDto events;
    TaskListDto tasks;
    SprintListDto sprints;
}
