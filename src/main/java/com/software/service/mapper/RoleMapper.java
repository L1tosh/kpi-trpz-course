package com.software.service.mapper;

import com.software.domain.user.Role;
import com.software.web.dto.role.RoleDto;
import com.software.web.dto.role.RoleListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    Role toRole(RoleDto roleDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    RoleDto toRoleDto(Role role);

    List<Role> toRole(List<RoleDto> roleDtos);
    List<RoleDto> toRoleDto(List<Role> roleList);

    default List<Role> toRoleList(RoleListDto roleListDto) {
        return toRole(roleListDto.getRoleDtos());
    }

    default RoleListDto toRoleListDto(List<Role> roleList) {
        if (roleList == null) return null;

        return RoleListDto.builder().roleDtos(toRoleDto(roleList)).build();
    }
}
