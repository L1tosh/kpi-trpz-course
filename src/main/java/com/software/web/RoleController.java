package com.software.web;

import com.software.dto.role.RoleDto;
import com.software.dto.role.RoleListDto;
import com.software.service.RoleService;
import com.software.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleMapper.toRoleDto(roleService.getRoleById(id)));
    }

    @GetMapping
    public ResponseEntity<RoleListDto> getAllRoles() {
        return ResponseEntity.ok(roleMapper.toRoleListDto(roleService.getAllRoles()));
    }
}
