package com.software.util.accesscontrol;

import com.software.util.accesscontrol.model.ActionEnum;

public interface RolePermissionService {
    boolean hasPermission(String roleName, ActionEnum action);
}
