package com.software.util.accesscontrol.handler;

import com.software.domain.user.Role;
import com.software.util.accesscontrol.model.ActionEnum;

public class ManagerHandler extends RoleHandler {
    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        if ("Manager".equals(role.getName())) {
            if (action == ActionEnum.CREATE || action == ActionEnum.VIEW || action == ActionEnum.UPDATE) {
                return true;
            }
        }
        return next != null && next.checkPermission(action, role);
    }
}
