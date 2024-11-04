package com.software.service.mapper;

import com.software.domain.Project;
import com.software.dto.project.ProjectDto;
import com.software.dto.project.ProjectEntry;
import com.software.dto.project.ProjectListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class, ItemMapper.class, SprintMapper.class})
public interface ProjectMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "toUser")
    Project toProject(ProjectDto projectDto);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "toUserDto")
    ProjectDto toProjectDto(Project project);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "toUserDto")
    @Mapping(source = "workers", target = "workers", qualifiedByName = "toUserListDto")
    @Mapping(source = "events", target = "events", qualifiedByName = "toEventListDto")
    @Mapping(source = "items", target = "items", qualifiedByName = "toItemListDto")
    @Mapping(source = "sprints", target = "sprints", qualifiedByName = "toSprintListDto")
    ProjectEntry toProjectEntry(Project project);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "toUser")
    @Mapping(source = "workers", target = "workers", qualifiedByName = "toUserList")
    @Mapping(source = "events", target = "events", qualifiedByName = "toEventList")
    @Mapping(source = "items", target = "items", qualifiedByName = "toItemList")
    @Mapping(source = "sprints", target = "sprints", qualifiedByName = "toSprintList")
    Project toProject(ProjectEntry projectEntry);

    List<ProjectDto> toProjectDto(List<Project> projects);

    default ProjectListDto toProjectListDto(List<Project> projectList) {
        if (projectList == null) return null;

        return ProjectListDto.builder().projectDtos(toProjectDto(projectList)).build();
    }

    void updateProject(Project source, @MappingTarget Project target);
}
