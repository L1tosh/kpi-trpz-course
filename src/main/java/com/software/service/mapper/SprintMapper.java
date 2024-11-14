package com.software.service.mapper;

import com.software.domain.Sprint;
import com.software.dto.sprint.SprintDto;
import com.software.dto.sprint.SprintListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface SprintMapper {

    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "command", target = "command")
    @Mapping(source = "items", target = "items", qualifiedByName = "toItemList")
    Sprint toSprint(SprintDto sprintDto);

    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "command", target = "command")
    @Mapping(source = "items", target = "items", qualifiedByName = "toItemListDto")
    SprintDto toSprintDto(Sprint sprint);

    List<Sprint> toSprint(List<SprintDto> sprintDtos);

    List<SprintDto> toSprintDto(List<Sprint> sprints);

    @Named("toSprintList")
    default List<Sprint> toSprintList(SprintListDto sprintListDto) {
        return toSprint(sprintListDto.getSprintDtos());
    }

    @Named("toSprintListDto")
    default SprintListDto toSprintListDto(List<Sprint> sprints) {
        if (sprints == null) return null;

        return SprintListDto.builder().sprintDtos(toSprintDto(sprints)).build();
    }
}