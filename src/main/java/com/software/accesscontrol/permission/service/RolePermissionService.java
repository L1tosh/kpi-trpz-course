package com.software.accesscontrol.permission.service;

import com.software.accesscontrol.permission.RolePermissionHandler;
import com.software.common.access.ActionEnum;

public interface RolePermissionService {
    boolean hasPermission(RolePermissionHandler rolePermissionHandlerChain, String roleName, ActionEnum action);
}
