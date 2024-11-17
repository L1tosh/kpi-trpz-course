package com.software.web;

import com.software.annotation.CheckUserInProject;
import com.software.dto.task.TaskDto;
import com.software.dto.task.TaskEntry;
import com.software.dto.task.TaskListDto;
import com.software.service.TaskService;
import com.software.service.mapper.TaskMapper;
import com.software.service.proxy.TaskServiceProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/projects/{projectId}/items")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskServiceProxy taskServiceProxy, TaskMapper taskMapper) {
        this.taskService = taskServiceProxy;
        this.taskMapper = taskMapper;
    }


    @CheckUserInProject
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskEntry> getTaskById(
            @PathVariable Long projectId,
            @PathVariable Long taskId) {
        return ResponseEntity.ok(taskMapper.toTaskEntry(taskService.getTaskById(taskId)));
    }

    @CheckUserInProject
    @GetMapping
    public ResponseEntity<TaskListDto> getAllTasks(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskMapper.toTaskListDto(taskService.getAllTasks(projectId)));
    }

    @CheckUserInProject
    @PostMapping
    public ResponseEntity<TaskEntry> createTask(
            @PathVariable Long projectId,
            @RequestBody TaskDto taskDto) {
        var task = taskMapper.toTask(taskDto);
        var createdTask = taskService.createTask(task, projectId);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.toTaskEntry(createdTask));
    }

    @CheckUserInProject
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskEntry> updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestBody TaskDto taskDto) {
        var updatedTask = taskMapper.toTask(taskDto);
        var task = taskService.updateTask(taskId, updatedTask);
        return ResponseEntity.ok(taskMapper.toTaskEntry(task));
    }

    @CheckUserInProject
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.noContent().build();
    }

}
