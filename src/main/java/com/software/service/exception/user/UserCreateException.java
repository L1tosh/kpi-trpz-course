package com.software.service.exception.user;


public class UserCreateException extends RuntimeException {
    private static final String USER_CREATE_MESSAGE = "User with email %s exist ";

    public UserCreateException(String email) {
        super(USER_CREATE_MESSAGE.formatted(email));
    }
}
