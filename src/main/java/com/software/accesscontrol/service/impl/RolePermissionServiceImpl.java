package com.software.accesscontrol.service.impl;

import com.software.accesscontrol.handler.RolePermissionHandler;
import com.software.accesscontrol.service.RolePermissionService;
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
