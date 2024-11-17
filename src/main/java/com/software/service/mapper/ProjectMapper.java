package com.software.service.mapper;

import com.software.domain.Project;
import com.software.dto.project.ProjectDto;
import com.software.dto.project.ProjectEntry;
import com.software.dto.project.ProjectListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EventMapper.class, TaskMapper.class, SprintMapper.class})
public interface ProjectMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner", target = "owner")
    Project toProject(ProjectDto projectDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner", target = "owner")
    ProjectDto toProjectDto(Project project);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "workers", target = "workers")
    @Mapping(source = "events", target = "events", qualifiedByName = "toEventListDto")
    @Mapping(source = "tasks", target = "tasks", qualifiedByName = "toTaskListDto")
    @Mapping(source = "sprints", target = "sprints", qualifiedByName = "toSprintListDto")
    ProjectEntry toProjectEntry(Project project);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "workers", target = "workers")
    @Mapping(source = "events", target = "events", qualifiedByName = "toEventList")
    @Mapping(source = "tasks", target = "tasks", qualifiedByName = "toTaskList")
    @Mapping(source = "sprints", target = "sprints", qualifiedByName = "toSprintList")
    Project toProject(ProjectEntry projectEntry);

    List<ProjectDto> toProjectDto(List<Project> projects);

    default ProjectListDto toProjectListDto(List<Project> projectList) {
        if (projectList == null) return null;

        return ProjectListDto.builder().projectDtos(toProjectDto(projectList)).build();
    }

    void updateProject(Project source, @MappingTarget Project target);
}
