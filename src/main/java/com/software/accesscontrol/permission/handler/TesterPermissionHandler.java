package com.software.accesscontrol.permission.handler;

import com.software.accesscontrol.permission.RolePermissionHandler;
import com.software.common.access.ActionEnum;
import com.software.domain.user.Role;

public class TesterPermissionHandler extends RolePermissionHandler {
    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        if ("Tester".equals(role.getName()) && (action == ActionEnum.CREATE || action == ActionEnum.VIEW || action == ActionEnum.UPDATE)) {
                return true;
            }

        return next != null && next.checkPermission(action, role);
    }
}
