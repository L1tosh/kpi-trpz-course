package com.software.util.accesscontrol.impl;

import com.software.domain.user.Role;
import com.software.util.accesscontrol.RolePermissionService;
import com.software.util.accesscontrol.handler.RolePermissionHandler;
import com.software.util.accesscontrol.model.ActionEnum;
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
