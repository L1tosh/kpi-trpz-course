package com.software.service.proxy;

import com.software.auth.jwt.JwtTokenUtil;
import com.software.domain.Task;
import com.software.domain.user.Role;
import com.software.service.TaskService;
import com.software.service.UserService;
import com.software.service.exception.access.AccessDeniedException;
import com.software.service.exception.role.RoleNotFoundException;
import com.software.service.impl.TaskServiceImpl;
import com.software.util.accesscontrol.RolePermissionService;
import com.software.util.accesscontrol.model.ActionEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceProxy implements TaskService {

    private final TaskServiceImpl taskService;
    private final RolePermissionService rolePermissionService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

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
    public Task getTaskById(Long taskId) {
        log.info("Fetching task with ID: {}", taskId);
        return taskService.getTaskById(taskId);
    }

    @Override
    @Transactional
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
    @Transactional
    public Task updateTask(Long taskId, Task updatedTask) {
        Long projectId = updatedTask.getProject().getId();
        log.info("Attempting to update task with ID: {}", taskId);
        if (hasPermissionForAnyRole(projectId, ActionEnum.UPDATE)) {
            Task updated = taskService.updateTask(taskId, updatedTask);
            log.info("Task updated with ID: {}", updated.getId());
            return updated;
        } else {
            log.error("Access denied for updating task with ID: {}", taskId);
            throw new AccessDeniedException();
        }
    }

    @Override
    @Transactional
    public void deleteTaskById(Long taskId) {
        Task taskToDelete = taskService.getTaskById(taskId);
        Long projectId = taskToDelete.getProject().getId();
        log.info("Attempting to delete task with ID: {}", taskId);
        if (hasPermissionForAnyRole(projectId, ActionEnum.DELETE)) {
            taskService.deleteTaskById(taskId);
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

        for (String roleName : roles) {
            if (rolePermissionService.hasPermission(roleName, action)) {
                log.info("Role {} has permission for action {}", roleName, action);
                return true;
            }
        }

        log.warn("User with roles {} does not have permission for action {}", roles, action);
        return false;
    }
}