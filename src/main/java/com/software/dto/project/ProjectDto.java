package com.software.dto.project;

import com.software.dto.user.UserDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectDto {

    @NotBlank(message = "name is mandatory")
    @Max(value = 100, message = "name length must not exceed 100 characters")
    String name;

    @NotNull(message = "description is mandatory")
    String description;

    @NotNull(message = "owner is mandatory")
    UserDto owner;
}
