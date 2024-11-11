package com.software.service.impl;

import com.software.data.user.UserRepository;
import com.software.domain.user.User;
import com.software.service.UserService;
import com.software.service.exception.user.UserCreateException;
import com.software.service.exception.user.UserNotFoundException;
import com.software.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.info("User with id {} not found", userId);
            return new UserNotFoundException(userId);
        });
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.info("User with email {} not found", email);
            return new UserNotFoundException(email);
        });
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserCreateException(user.getEmail());
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        var userToUpdate = getUserById(userId);
        userMapper.updateUser(updatedUser, userToUpdate);

        return userRepository.save(userToUpdate);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            log.warn("Attempt to delete user with id {}", userId);
        }
    }

    @Override
    @Transactional
    public User saveUser(User userToSave) {
        return userRepository.save(userToSave);
    }
}
