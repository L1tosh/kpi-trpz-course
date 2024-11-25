package com.software.accesscontrol.service;

import com.software.accesscontrol.handler.RolePermissionHandler;
import com.software.common.access.ActionEnum;

public interface RolePermissionService {
    boolean hasPermission(RolePermissionHandler rolePermissionHandlerChain, String roleName, ActionEnum action);
}
