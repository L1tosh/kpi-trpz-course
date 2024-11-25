package com.software.accesscontrol.impl;

import com.software.accesscontrol.RolePermissionService;
import com.software.accesscontrol.handler.RolePermissionHandler;
import com.software.accesscontrol.model.ActionEnum;
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
