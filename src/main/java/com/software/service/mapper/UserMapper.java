package com.software.service.mapper;

import com.software.domain.user.Role;
import com.software.domain.user.User;
import com.software.dto.role.RoleDto;
import com.software.dto.role.RoleListDto;
import com.software.dto.user.UserDto;
import com.software.dto.user.UserListDto;
import com.software.dto.user.UserRegistrationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "toRoleList")
    @Named("toUser")
    User toUser(UserDto userDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "toRoleListDto")
    @Named("toUserDto")
    UserDto toUserDto(User user);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "surname", target = "surname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User toUser(UserRegistrationDto userRegistrationDto);

    List<User> toUser(List<UserDto> userDtoList);
    List<UserDto> toUserDto(List<User> userList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateUser(User updatedUser, @MappingTarget User userToUpdate);

    @Named("toUserListDto")
    default UserListDto toUserListDto(List<User> userList) {
        if (userList == null) return null;

        return UserListDto.builder().userDtos(
                userList.stream()
                        .map(this::toUserDto)
                        .toList()
        ).build();
    }

    @Named("toUserList")
    default List<User> toUserList(UserListDto userListDto) {
        if (userListDto == null || userListDto.getUserDtos() == null) return Collections.emptyList();

        return userListDto.getUserDtos().stream()
                .map(this::toUser)
                .toList();
    }

    @Named("toRoleList")
    default List<Role> toRoleList(RoleListDto roleListDto) {
        if (roleListDto == null || roleListDto.getRoleDtos() == null) return Collections.emptyList();

        return roleListDto.getRoleDtos().stream()
                .map(INSTANCE::toRole)
                .toList();
    }

    @Named("toRoleListDto")
    default RoleListDto toRoleListDto(List<Role> roles) {
        if (roles == null) return null;

        List<RoleDto> roleDtos = roles.stream()
                .map(INSTANCE::toRoleDto)
                .toList();
        return RoleListDto.builder().roleDtos(roleDtos).build();
    }

    default List<Role> map(RoleListDto roleListDto) {
        return toRoleList(roleListDto);
    }

    default RoleListDto map(List<Role> roles) {
        if (roles == null) return null;

        return toRoleListDto(roles);
    }
}