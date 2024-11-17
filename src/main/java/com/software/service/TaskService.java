package com.software.service;

import com.software.domain.Task;

import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    List<Task> getAllTasks(Long projectId);
    Task getTaskById(Long taskId);
    Task createTask(Task task, Long projectId);
    Task updateTask(Long taskId, Task updatedTask);
    void deleteTaskById(Long taskId);

}
