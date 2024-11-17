package com.software.util.accesscontrol.handler;

import com.software.domain.user.Role;
import com.software.util.accesscontrol.model.ActionEnum;

public class AdministratorHandler extends RoleHandler {

    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        return "Administrator".equals(role.getName());  // Администратор может выполнять все действия
    }
}
