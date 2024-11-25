package com.software.web;

import com.software.accesscontrol.annotation.CheckUserInProject;
import com.software.domain.user.Role;
import com.software.service.UserService;
import com.software.service.mapper.RoleMapper;
import com.software.service.mapper.UserProjectRoleMapper;
import com.software.web.dto.role.RoleDto;
import com.software.web.dto.role.RoleListDto;
import com.software.web.dto.user.UserDto;
import com.software.web.dto.user.UserListDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects/{projectId}/workers")
public class ProjectUserController {

    private final UserService userService;
    private final RoleMapper roleMapper;
    private final UserProjectRoleMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListDto> getProjectUsers(@PathVariable Long projectId) {
        var projectUsers = userService.getProjectUsers(projectId);
        var userList = mapProjectUsersToUserListDto(projectUsers);
        return ResponseEntity.ok(UserListDto.builder().staff(userList).build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserRolesInProject(
            @PathVariable Long projectId,
            @PathVariable Long userId) {
        var workerRoles = userService.getUserRolesInProject(projectId, userId);
        return ResponseEntity.ok(userMapper.toUserDto(projectId, workerRoles));
    }

    @CheckUserInProject
    @PostMapping("/{userId}")
    public ResponseEntity<UserDto> addRoleToUser(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestBody @Valid RoleDto roleDto) {

        userService.addRoleToUser(projectId, userId, roleMapper.toRole(roleDto));
        var workerRoles = userService.getUserRolesInProject(projectId, userId);

        return ResponseEntity.ok(userMapper.toUserDto(projectId, workerRoles));
    }

    @CheckUserInProject
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> removeRolesFromUser(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestBody @Valid RoleListDto roleDtos) {

        userService.removeRolesFromUser(projectId, userId, roleMapper.toRoleList(roleDtos));
        var workerRoles = userService.getUserRolesInProject(projectId, userId);

        return ResponseEntity.ok(userMapper.toUserDto(projectId, workerRoles));
    }

    @CheckUserInProject
    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<Void> removeUserFromProject(
            @PathVariable Long projectId,
            @PathVariable Long userId) {
        userService.removeWorkerFromProject(projectId, userId);
        return ResponseEntity.noContent().build();
    }

    private List<UserDto> mapProjectUsersToUserListDto(Map<Long, List<Role>> projectUsers) {
        return projectUsers.entrySet().stream()
                .map(entry -> UserDto.builder()
                        .userId(entry.getKey())
                        .roles(roleMapper.toRoleListDto(entry.getValue()))
                        .build())
                .toList();
    }
}
