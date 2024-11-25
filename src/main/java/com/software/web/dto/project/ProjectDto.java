package com.software.web.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProjectDto {

    @NotBlank(message = "name is mandatory")
    @Size(max = 100, message = "name length must not exceed 100 characters")
    String name;

    @NotNull(message = "description is mandatory")
    String description;

    @NotNull(message = "owner is mandatory")
    Long owner;
}
