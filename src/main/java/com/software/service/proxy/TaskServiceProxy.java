package com.software.service.proxy;

import com.software.accesscontrol.factory.PermissionHandlerFactory;
import com.software.accesscontrol.service.RolePermissionService;
import com.software.auth.jwt.JwtTokenUtil;
import com.software.common.access.ActionEnum;
import com.software.domain.Task;
import com.software.domain.user.Role;
import com.software.service.TaskService;
import com.software.service.UserService;
import com.software.service.exception.access.AccessDeniedException;
import com.software.service.exception.role.RoleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskServiceProxy implements TaskService {

    private final TaskService taskService;
    private final RolePermissionService rolePermissionService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    private PermissionHandlerFactory permissionHandlerFactory;

    @Autowired
    public TaskServiceProxy(
            @Qualifier("taskServiceImpl") TaskService taskService,
            @Qualifier("userServiceImpl") UserService userService,
            RolePermissionService rolePermissionService,
            JwtTokenUtil jwtTokenUtil) {
        this.taskService = taskService;
        this.rolePermissionService = rolePermissionService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @Autowired
    @Qualifier("taskPermissionHandlerFactory")
    public void setAccessHandlerFactory(PermissionHandlerFactory permissionHandlerFactory) {
        this.permissionHandlerFactory = permissionHandlerFactory;
    }

    @Override
    public List<Task> getAllTasks() {
        log.info("Fetching all tasks");
        return taskService.getAllTasks();
    }

    @Override
    @Cacheable(value = "tasks", key = "#projectId", unless = "#result == null")
    public List<Task> getAllTasks(Long projectId) {
        log.info("Fetching tasks for project with ID: {}", projectId);
        return taskService.getAllTasks(projectId);
    }

    @Override
    @Cacheable(value = "task", key = "#taskId", unless = "#result == null")
    public Task getTaskById(Long projectId, Long taskId) {
        log.info("Fetching task with ID: {}", taskId);
        return taskService.getTaskById(projectId, taskId);
    }

    @Override
    @CachePut(value = "task", key = "#result.id")
    @CacheEvict(value = "tasks", key = "#projectId")
    public Task createTask(Task task, Long projectId) {
        log.info("Attempting to create task for project with ID: {}", projectId);
        if (hasPermissionForAnyRole(projectId, ActionEnum.CREATE)) {
            Task createdTask = taskService.createTask(task, projectId);
            log.info("Task created with ID: {}", createdTask.getId());
            return createdTask;
        } else {
            log.error("Access denied for creating task in project with ID: {}", projectId);
            throw new AccessDeniedException();
        }
    }

    @Override
    @CachePut(value = "task", key = "#taskId")
    @CacheEvict(value = "tasks", key = "#updatedTask.project.id")
    public Task updateTask(Long projectId, Long taskId, Task updatedTask) {
        log.info("Attempting to update task with ID: {}", taskId);
        if (hasPermissionForAnyRole(projectId, ActionEnum.UPDATE)) {
            Task updated = taskService.updateTask(projectId, taskId, updatedTask);
            log.info("Task updated with ID: {}", updated.getId());
            return updated;
        } else {
            log.error("Access denied for updating task with ID: {}", taskId);
            throw new AccessDeniedException();
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "task", key = "#taskId"),
            @CacheEvict(value = "tasks", allEntries = true)
    })
    public void deleteTaskById(Long projectId, Long taskId) {
        log.info("Attempting to delete task with ID: {}", taskId);
        if (hasPermissionForAnyRole(projectId, ActionEnum.DELETE)) {
            taskService.deleteTaskById(projectId, taskId);
            log.info("Task with ID: {} deleted", taskId);
        } else {
            log.error("Access denied for deleting task with ID: {}", taskId);
            throw new AccessDeniedException();
        }
    }

    private boolean hasPermissionForAnyRole(Long projectId, ActionEnum action) {
        var userPrincipal = jwtTokenUtil.extractUserPrincipalFromContext();

        List<String> roles = userService.getUserRolesInProject(projectId, userPrincipal.getUserId())
                .stream()
                .map(Role::getName)
                .toList();

        if (roles.isEmpty()) {
            log.error("No roles found for user in project with ID: {}", projectId);
            throw new RoleNotFoundException();
        }

        var accessHandlerChain = permissionHandlerFactory.createAccessHandlerChain();

        for (String roleName : roles) {
            if (rolePermissionService.hasPermission(accessHandlerChain, roleName, action)) {
                log.info("Role {} has permission for action {}", roleName, action);
                return true;
            }
        }

        log.warn("User with roles {} does not have permission for action {}", roles, action);
        return false;
    }
}
