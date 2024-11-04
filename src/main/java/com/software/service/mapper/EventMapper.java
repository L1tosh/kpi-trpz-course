package com.software.service.mapper;

import com.software.domain.Event;
import com.software.dto.event.EventDto;
import com.software.dto.event.EventListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, StatusMapper.class,})
public interface EventMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Named("toEvent")
    Event toEvent(EventDto eventDto);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "createTime", target = "createTime")
    @Named("toEventDto")
    EventDto toEventDto(Event event);

    List<EventDto> toEventDto(List<Event> eventList);
    List<Event> toEvent(List<EventDto> eventDtoList);

    @Named("toEventListDto")
    default EventListDto toEventListDto(List<Event> events) {
        if (events == null) return null;

        return EventListDto.builder().eventDtos(toEventDto(events)).build();
    }

    @Named("toEventList")
    default List<Event> toEventList(EventListDto eventListDto) {
        return toEvent(eventListDto.getEventDtos());
    }
}
