package com.software.accesscontrol.permission.service.impl;

import com.software.accesscontrol.permission.RolePermissionHandler;
import com.software.accesscontrol.permission.service.RolePermissionService;
import com.software.common.access.ActionEnum;
import com.software.domain.user.Role;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionServiceImpl implements RolePermissionService {

    @Override
    public boolean hasPermission(RolePermissionHandler rolePermissionHandlerChain, String roleName, ActionEnum action) {
        var role = new Role();
        role.setName(roleName);

        return rolePermissionHandlerChain.checkPermission(action, role);
    }
}
