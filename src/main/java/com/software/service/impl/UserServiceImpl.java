package com.software.service.impl;

import com.software.data.ProjectRepository;
import com.software.data.RoleRepository;
import com.software.data.UserProjectRoleRepository;
import com.software.domain.user.Role;
import com.software.domain.user.UserProjectRole;
import com.software.service.UserService;
import com.software.service.exception.project.ProjectNotFoundException;
import com.software.service.exception.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final ProjectRepository projectRepository;
    private final RoleRepository roleRepository;
    private final UserProjectRoleRepository userRepository;

    @Override
    public Map<Long, List<Role>> getProjectUsers(Long projectId) {
        if (projectRepository.findById(projectId).isEmpty()) {
            throw new ProjectNotFoundException(projectId);
        }

        return userRepository.findByProjectId(projectId).stream()
                .collect(Collectors.groupingBy(
                        UserProjectRole::getUserId,
                        Collectors.mapping(UserProjectRole::getRole, Collectors.toList())
                ));
    }

    @Override
    public List<Role> getUserRolesInProject(Long projectId, Long userId) {
        var projectWorkers = getProjectUsers(projectId);

        if (!projectWorkers.containsKey(userId)) {
            throw new UserNotFoundException(userId);
        }

        return projectWorkers.get(userId);
    }

    @Override
    @Transactional
    public void addRoleToUser(Long projectId, Long userId, List<Role> roles) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id : " + projectId));

        var roleIds = roles.stream().map(Role::getId).toList();

        var existingRoleIds = roleRepository.findAllById(roleIds).stream()
                .map(Role::getId)
                .collect(Collectors.toSet());


        var missingRoles = roleIds.stream()
                .filter(roleId -> !existingRoleIds.contains(roleId))
                .toList();

        if (!missingRoles.isEmpty()) {
            throw new IllegalArgumentException("Some roles not found with ids: " + missingRoles);
        }

        var userProjectRoles = roles.stream()
                .filter(role -> existingRoleIds.contains(role.getId()))
                .map(role -> UserProjectRole.builder()
                        .project(project)
                        .role(role)
                        .userId(userId)
                        .build())
                .toList();

        userRepository.saveAll(userProjectRoles);
    }

    @Override
    @Transactional
    public void removeRolesFromUser(Long projectId, Long userId, List<Role> roles) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with id: " + projectId));

        var existingUserRoles = userRepository.findByUserIdAndProjectId(userId, projectId);

        var rolesToRemove = roles.stream()
                .filter(role -> existingUserRoles.stream()
                        .anyMatch(userProjectRole -> userProjectRole.getRole().equals(role)))
                .toList();

        if (!rolesToRemove.isEmpty()) {
            var rolesToDelete = existingUserRoles.stream()
                    .filter(userProjectRole -> rolesToRemove.contains(userProjectRole.getRole()))
                    .toList();

            userRepository.deleteAll(rolesToDelete);
        }


        var remainingRoles = userRepository.findByUserIdAndProjectId(userId, projectId);
        if (remainingRoles.isEmpty()) {
            var basicWorkerRole = roleRepository.findByName("worker")
                    .orElseThrow(() -> new IllegalArgumentException("Basic role 'worker' not found"));

            var newUserProjectRole = UserProjectRole.builder()
                    .project(project)
                    .role(basicWorkerRole)
                    .userId(userId)
                    .build();

            userRepository.save(newUserProjectRole);
        }
    }

    @Override
    @Transactional
    public void removeWorkerFromProject(Long projectId, Long userId) {
        if (projectRepository.findById(projectId).isEmpty()) {
            throw new IllegalArgumentException("Project not found with id: " + projectId);
        }

        var userProjectRoles =
                userRepository.findByUserIdAndProjectId(projectId, userId);

        if (userProjectRoles.isEmpty()) {
            throw new IllegalArgumentException("User not found in the project with id: " + projectId);
        }

        userRepository.deleteAll(userProjectRoles);
    }
}