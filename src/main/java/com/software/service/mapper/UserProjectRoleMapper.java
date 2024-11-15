package com.software.service.mapper;

import com.software.domain.user.Role;
import com.software.dto.role.RoleDto;
import com.software.dto.role.RoleListDto;
import com.software.dto.user.UserDto;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserProjectRoleMapper {
    default UserDto toUserDto(Long userId, List<Role> roles) {
        return UserDto.builder()
                .userId(userId)
                .roles(toRoleListDto(roles))
                .build();
    }

    default RoleListDto toRoleListDto(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return new RoleListDto(Collections.emptyList());
        }
        return RoleListDto.builder()
                .roleDtos(roles.stream()
                        .map(this::toRoleDto)
                        .toList())
                .build();
    }

    RoleDto toRoleDto(Role role);
}
