package com.software.accesscontrol;

import com.software.accesscontrol.handler.RolePermissionHandler;
import com.software.accesscontrol.model.ActionEnum;

public interface RolePermissionService {
    boolean hasPermission(RolePermissionHandler rolePermissionHandlerChain, String roleName, ActionEnum action);
}
