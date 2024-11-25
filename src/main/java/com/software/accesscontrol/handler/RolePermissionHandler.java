package com.software.accesscontrol.handler;

import com.software.common.access.ActionEnum;
import com.software.domain.user.Role;
import lombok.Setter;

@Setter
public abstract class RolePermissionHandler {

    protected RolePermissionHandler next;

    public abstract boolean checkPermission(ActionEnum action, Role role);
}
