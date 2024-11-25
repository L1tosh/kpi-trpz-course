package com.software.accesscontrol.handler;

import com.software.accesscontrol.model.ActionEnum;
import com.software.domain.user.Role;

public class OwnerPermissionHandler extends RolePermissionHandler {
    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        return "Owner".equals(role.getName());
    }
}
