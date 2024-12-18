package com.software.accesscontrol.permission.factory;

import com.software.accesscontrol.permission.RolePermissionHandler;
import com.software.accesscontrol.permission.handler.AdministratorPermissionHandler;
import com.software.accesscontrol.permission.handler.OwnerPermissionHandler;
import org.springframework.stereotype.Component;

@Component
public class ProjectPermissionHandlerFactory implements PermissionHandlerFactory {
    @Override
    public RolePermissionHandler createAccessHandlerChain() {
        var ownerAccessHandler = new OwnerPermissionHandler();
        var administratorAccessHandler = new AdministratorPermissionHandler();

        ownerAccessHandler.setNext(administratorAccessHandler);

        return ownerAccessHandler;
    }
}
