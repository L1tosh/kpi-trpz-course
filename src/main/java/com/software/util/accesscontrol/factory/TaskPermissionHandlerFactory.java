package com.software.util.accesscontrol.factory;

import com.software.util.accesscontrol.handler.*;
import org.springframework.stereotype.Component;

@Component
public class TaskPermissionHandlerFactory implements PermissionHandlerFactory {
    @Override
    public RolePermissionHandler createAccessHandlerChain() {
        var ownerAccessHandler = new OwnerPermissionHandler();
        var administratorAccessHandler = new AdministratorPermissionHandler();
        var managerAccessHandler = new ManagerPermissionHandler();
        var testerAccessHandler = new TesterPermissionHandler();
        var developerAccessHandler = new DeveloperPermissionHandler();
        var viewerAccessHandler = new ViewerPermissionHandler();

        viewerAccessHandler.setNext(developerAccessHandler);
        developerAccessHandler.setNext(testerAccessHandler);
        testerAccessHandler.setNext(managerAccessHandler);
        managerAccessHandler.setNext(administratorAccessHandler);
        administratorAccessHandler.setNext(ownerAccessHandler);

        return viewerAccessHandler;
    }
}
