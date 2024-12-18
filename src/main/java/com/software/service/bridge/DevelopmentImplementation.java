package com.software.service.bridge;

import com.software.common.task.TaskStatus;

import java.util.List;

public interface DevelopmentImplementation<T> {
    T saveEntity(Long projectId, T entity);
    List<T> findAllEntities(Long projectId);
    T getByIdEntity(Long projectId, Long id);
    void deleteEntity(Long projectId, Long id);
    void validateEntity(T entity);
    void updateTaskStatus(Long taskId, TaskStatus newStatus);
    boolean isEntityAssociatedWithProject(Long projectId, Long entityId);
}
