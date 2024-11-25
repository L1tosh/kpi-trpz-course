package com.software.accesscontrol.handler;

import com.software.accesscontrol.model.ActionEnum;
import com.software.domain.user.Role;

public class AdministratorPermissionHandler extends RolePermissionHandler {

    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        return "Administrator".equals(role.getName());
    }
}
