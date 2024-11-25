package com.software.util.accesscontrol.factory;

import com.software.util.accesscontrol.handler.RolePermissionHandler;

public interface PermissionHandlerFactory {
    RolePermissionHandler createAccessHandlerChain();
}
