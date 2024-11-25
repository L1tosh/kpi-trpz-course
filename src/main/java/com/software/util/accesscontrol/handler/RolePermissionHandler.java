package com.software.util.accesscontrol.handler;

import com.software.domain.user.Role;
import com.software.util.accesscontrol.model.ActionEnum;
import lombok.Setter;

@Setter
public abstract class RolePermissionHandler {

    protected RolePermissionHandler next;

    public abstract boolean checkPermission(ActionEnum action, Role role);
}
