package com.software.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleDto {
    Integer id;

    @NotBlank(message = "name is mandatory")
    String name;
}
