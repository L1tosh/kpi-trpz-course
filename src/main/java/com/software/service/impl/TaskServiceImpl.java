package com.software.service.impl;

import com.software.data.ProjectRepository;
import com.software.data.TaskRepository;
import com.software.domain.Task;
import com.software.service.TaskService;
import com.software.service.exception.project.ProjectNotFoundException;
import com.software.service.exception.task.TaskNotFoundException;
import com.software.service.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllTasks(Long projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.info("Project with if {} not found", projectId);
                    return new ProjectNotFoundException(projectId);
                }
        );
        return taskRepository.findByProject(project);
    }

    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.info("Item with id {} not found", taskId);
                    return new TaskNotFoundException(taskId);
                });
    }

    @Override
    @Transactional
    public Task createTask(Task task, Long projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
        task.setProject(project);
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Task updateTask(Long taskId, Task updatedTask) {
        var taskToUpdate = getTaskById(taskId);

        if (!taskToUpdate.getProject().getId().equals(updatedTask.getProject().getId())) {
            throw new IllegalStateException("Task belongs to a different project");
        }

        taskMapper.updateItem(updatedTask, taskToUpdate);

        return taskRepository.save(taskToUpdate);
    }

    @Override
    @Transactional
    public void deleteTaskById(Long taskId) {
        if (taskRepository.findById(taskId).isPresent()) {
            taskRepository.deleteById(taskId);

        } else {
            log.warn("Attempt to delete item with id {}", taskId);
        }
    }
}
