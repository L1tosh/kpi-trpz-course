package com.software.service.exception.task;

public class TaskNotFoundException extends RuntimeException {
    private static final String TASK_NOT_FOUND_MESSAGE = "Task with %d not found";

    public TaskNotFoundException(Long taskId) {
        super(TASK_NOT_FOUND_MESSAGE.formatted(taskId));
    }

}
