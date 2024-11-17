package com.software.service.exception.role;

public class RoleNotFoundException extends RuntimeException {
    private static final String ROLE_NOT_FOUND_ID_MESSAGE = "Role with id %d not found";
    private static final String ROLE_NOT_FOUND_NAME_MESSAGE = "Role with name %s not found";
    private static final String ROLE_NOT_FOUND_MESSAGE = "No roles found for the user in the project";

    public RoleNotFoundException() {
        super(ROLE_NOT_FOUND_MESSAGE);
    }

    public RoleNotFoundException(Long roleId) {
        super(ROLE_NOT_FOUND_ID_MESSAGE.formatted(roleId));
    }

    public RoleNotFoundException(String roleName) {
        super(ROLE_NOT_FOUND_NAME_MESSAGE.formatted(roleName));
    }
}
