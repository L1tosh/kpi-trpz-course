package com.software.dto.event;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class EventListDto {
    List<EventDto> eventDtos;
}
