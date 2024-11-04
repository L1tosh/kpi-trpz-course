package com.software.dto.role;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RoleListDto {
    List<RoleDto> roleDtos;
}
