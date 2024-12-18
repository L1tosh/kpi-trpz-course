package com.software.accesscontrol.permission.factory;

import com.software.accesscontrol.permission.RolePermissionHandler;

public interface PermissionHandlerFactory {
    RolePermissionHandler createAccessHandlerChain();
}
