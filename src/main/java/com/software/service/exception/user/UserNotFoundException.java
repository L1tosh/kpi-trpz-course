package com.software.service.exception.user;

public class UserNotFoundException extends RuntimeException {

    private static final String USER_NOT_FOUND_ID_MESSAGE = "User with id %d not found";
    private static final String USER_NOT_FOUND_NAME_MESSAGE = "User with email %s not found";

    public UserNotFoundException(Long userId) {
        super(USER_NOT_FOUND_ID_MESSAGE.formatted(userId));
    }

    public UserNotFoundException(String email) {
        super(USER_NOT_FOUND_NAME_MESSAGE.formatted(email));
    }
}
