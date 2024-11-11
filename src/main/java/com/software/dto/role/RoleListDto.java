package com.software.dto.role;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class RoleListDto {
    @JsonProperty("roleDtos")
    List<RoleDto> roleDtos;
}
