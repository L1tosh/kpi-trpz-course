package com.software.web.dto.project;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ProjectListDto {
    List<ProjectDto> projectDtos;
}
