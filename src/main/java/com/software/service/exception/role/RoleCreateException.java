package com.software.service.exception.role;

public class RoleCreateException extends RuntimeException{
    private static final String ROLE_CREATE_MESSAGE = "Role with name %s exist";

    public RoleCreateException(String roleName) {
        super(ROLE_CREATE_MESSAGE.formatted(roleName));
    }
}
