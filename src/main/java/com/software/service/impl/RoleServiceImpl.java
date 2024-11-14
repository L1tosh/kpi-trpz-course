package com.software.service.impl;

import com.software.data.RoleRepository;
import com.software.domain.user.Role;
import com.software.service.RoleService;
import com.software.service.exception.role.RoleCreateException;
import com.software.service.exception.role.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(Integer roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> {
                    log.info("Role with id {} not found", roleId);
                    return new RoleNotFoundException(roleId);
                });
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> {
                    log.info("Role with name {} not found", roleName);
                    return new RoleNotFoundException(roleName);
                });
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role creteRole(Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            log.info("Role with name {} alredy exist", role.getName());
            throw new RoleCreateException(role.getName());
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Integer roleId) {
        if (roleRepository.findById(roleId).isPresent()) {
            roleRepository.deleteById(roleId);
        } else {
            log.info("Attempt to delete role with non-existent id {}", roleId);
        }
    }
}
