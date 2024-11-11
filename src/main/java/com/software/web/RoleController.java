package com.software.web;

import com.software.dto.role.RoleDto;
import com.software.dto.role.RoleListDto;
import com.software.service.RoleService;
import com.software.service.mapper.RoleMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.ok(roleMapper.toRoleDto(roleService.getRoleById(id)));
    }

    @GetMapping
    public ResponseEntity<RoleListDto> getAllRoles() {
        return ResponseEntity.ok(roleMapper.toRoleListDto(roleService.getAllRoles()));
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody @Valid RoleDto roleToSave) {
        var savedRole = roleService.creteRole(roleMapper.toRole(roleToSave));

        return ResponseEntity
                .created(URI.create("/api/v1/roles?name=%s".formatted(roleToSave.getName())))
                .body(roleMapper.toRoleDto(savedRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }

}
