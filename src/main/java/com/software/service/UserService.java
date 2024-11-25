package com.software.service;

import com.software.domain.user.Role;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<Long, List<Role>> getProjectUsers(Long projectId);
    List<Role> getUserRolesInProject(Long projectId, Long userId);
    void addRoleToUser(Long projectId, Long userId, Role role);
    void removeRolesFromUser(Long projectId, Long userId, List<Role> roles);
    void removeWorkerFromProject(Long projectId, Long userId);
}
