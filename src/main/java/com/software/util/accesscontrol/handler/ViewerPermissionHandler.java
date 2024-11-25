package com.software.util.accesscontrol.handler;

import com.software.domain.user.Role;
import com.software.util.accesscontrol.model.ActionEnum;

public class ViewerPermissionHandler extends RolePermissionHandler {
    @Override
    public boolean checkPermission(ActionEnum action, Role role) {
        if ("Viewer".equals(role.getName()) && action == ActionEnum.VIEW) {
            return true;
        }
        return next != null && next.checkPermission(action, role);
    }
}
