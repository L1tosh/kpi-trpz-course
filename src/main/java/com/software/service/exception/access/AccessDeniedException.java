package com.software.service.exception.access;

public class AccessDeniedException extends RuntimeException {

    private static final String ACCESS_DENIED_MESSAGE = "Access denied for the current roles";

    public AccessDeniedException() {
        super(ACCESS_DENIED_MESSAGE);
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
