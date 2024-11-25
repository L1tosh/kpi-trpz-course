package com.software.util.accesscontrol;

import com.software.util.accesscontrol.handler.RolePermissionHandler;
import com.software.util.accesscontrol.model.ActionEnum;

public interface RolePermissionService {
    boolean hasPermission(RolePermissionHandler rolePermissionHandlerChain, String roleName, ActionEnum action);
}
