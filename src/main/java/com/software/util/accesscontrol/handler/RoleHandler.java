package com.software.util.accesscontrol.handler;

import com.software.domain.user.Role;
import com.software.util.accesscontrol.model.ActionEnum;

public abstract class RoleHandler {

    protected RoleHandler next;

    // Устанавливаем следующий обработчик
    public void setNext(RoleHandler next) {
        this.next = next;
    }

    // Абстрактный метод для проверки прав
    public abstract boolean checkPermission(ActionEnum action, Role role);
}
