package com.software.accesscontrol.permission.handler;

import com.software.accesscontrol.permission.RolePermissionHandler;
import com.software.common.access.ActionEnum;
import com.software.domain.user.Role;

public class OwnerPermissionHandler extends RolePermissionHandler {
    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        return "Owner".equals(role.getName());
    }
}
