package com.software.accesscontrol.handler;

import com.software.common.access.ActionEnum;
import com.software.domain.user.Role;

public class ViewerPermissionHandler extends RolePermissionHandler {
    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        if ("Viewer".equals(role.getName()) && action == ActionEnum.VIEW) {
            return true;
        }
        return next != null && next.checkPermission(action, role);
    }
}
