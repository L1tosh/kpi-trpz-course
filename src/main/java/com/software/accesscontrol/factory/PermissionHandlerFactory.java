package com.software.accesscontrol.factory;

import com.software.accesscontrol.handler.RolePermissionHandler;

public interface PermissionHandlerFactory {
    RolePermissionHandler createAccessHandlerChain();
}
