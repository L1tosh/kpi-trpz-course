package com.software.web.dto.sprint;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SprintListDto {
    List<SprintDto> sprintDtos;
}
