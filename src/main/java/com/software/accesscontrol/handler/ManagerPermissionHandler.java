package com.software.accesscontrol.handler;

import com.software.common.access.ActionEnum;
import com.software.domain.user.Role;

public class ManagerPermissionHandler extends RolePermissionHandler {
    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        if ("Manager".equals(role.getName()) && (action == ActionEnum.CREATE || action == ActionEnum.VIEW || action == ActionEnum.UPDATE)) {
                return true;
            }

        return next != null && next.checkPermission(action, role);
    }
}
