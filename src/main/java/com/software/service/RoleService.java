package com.software.service;

import com.software.domain.user.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long roleId);
    Role getRoleByName(String roleName);
    List<Role> getAllRoles();
    Role creteRole(Role role);
    void deleteRole(Long roleId);
}
