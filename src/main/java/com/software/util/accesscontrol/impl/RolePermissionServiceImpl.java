package com.software.util.accesscontrol.impl;

import com.software.domain.user.Role;
import com.software.util.accesscontrol.RolePermissionService;
import com.software.util.accesscontrol.handler.AdministratorHandler;
import com.software.util.accesscontrol.handler.DeveloperHandler;
import com.software.util.accesscontrol.handler.RoleHandler;
import com.software.util.accesscontrol.model.ActionEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionServiceImpl implements RolePermissionService {

    private RoleHandler roleHandlerChain;

    @PostConstruct
    public void init() {

        var administratorHandler = new AdministratorHandler();
        var developerHandler = new DeveloperHandler();

        administratorHandler.setNext(developerHandler);

        this.roleHandlerChain = administratorHandler;
    }

    @Override
    public boolean hasPermission(String roleName, ActionEnum action) {
        var role = new Role();
        role.setName(roleName);

        return roleHandlerChain.checkPermission(action, role);
    }
}
