package com.software.dto.project;

import com.software.dto.event.EventListDto;
import com.software.dto.item.ItemListDto;
import com.software.dto.sprint.SprintListDto;
import com.software.dto.user.UserDto;
import com.software.dto.user.UserListDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectEntry {
    Long id;
    String name;
    String description;
    UserDto owner;
    UserListDto workers;
    EventListDto events;
    ItemListDto items;
    SprintListDto sprints;
}
