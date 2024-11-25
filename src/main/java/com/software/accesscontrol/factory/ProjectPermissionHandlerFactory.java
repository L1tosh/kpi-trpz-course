package com.software.accesscontrol.factory;

import com.software.accesscontrol.handler.AdministratorPermissionHandler;
import com.software.accesscontrol.handler.OwnerPermissionHandler;
import com.software.accesscontrol.handler.RolePermissionHandler;
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
