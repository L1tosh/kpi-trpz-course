package com.software.util.accesscontrol.handler;

import com.software.domain.user.Role;
import com.software.util.accesscontrol.model.ActionEnum;

public class AdministratorPermissionHandler extends RolePermissionHandler {

    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        return "Administrator".equals(role.getName());
    }
}
