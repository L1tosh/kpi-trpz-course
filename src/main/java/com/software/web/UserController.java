package com.software.web;

import com.software.dto.user.UserDto;
import com.software.dto.user.UserListDto;
import com.software.dto.user.UserRegistrationDto;
import com.software.service.UserService;
import com.software.service.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toUserDto(userService.getUserById(id)));
    }

    @GetMapping
    public ResponseEntity<UserListDto> getAllUsers() {
        return ResponseEntity.ok(userMapper.toUserListDto(userService.getAllUsers()));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserRegistrationDto userToSave) {
        var savedUser = userService.createUser(userMapper.toUser(userToSave));

        return ResponseEntity
                .created(URI.create("/api/v1/users/%d".formatted(savedUser.getId())))
                .body(userMapper.toUserDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody @Valid UserDto updatedUser) {
        var savedUser = userService.updateUser(id, userMapper.toUser(updatedUser));

        return ResponseEntity.ok(userMapper.toUserDto(savedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
