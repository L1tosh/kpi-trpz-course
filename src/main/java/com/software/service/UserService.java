package com.software.service;

import com.software.domain.user.User;

import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User createUser(User user);
    User updateUser(Long userId, User updatedUser);
    void deleteUser(Long userId);
    User saveUser(User userToSave);

}
