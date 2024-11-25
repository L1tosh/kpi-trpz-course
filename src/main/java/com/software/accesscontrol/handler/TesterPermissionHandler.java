package com.software.accesscontrol.handler;

import com.software.accesscontrol.model.ActionEnum;
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
