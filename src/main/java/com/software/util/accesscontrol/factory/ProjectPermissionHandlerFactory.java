package com.software.util.accesscontrol.factory;

import com.software.util.accesscontrol.handler.AdministratorPermissionHandler;
import com.software.util.accesscontrol.handler.OwnerPermissionHandler;
import com.software.util.accesscontrol.handler.RolePermissionHandler;
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
