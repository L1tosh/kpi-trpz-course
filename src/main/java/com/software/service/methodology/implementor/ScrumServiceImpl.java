package com.software.service.methodology.implementor;

import com.software.common.task.TaskStatus;
import com.software.common.task.TaskType;
import com.software.data.ProjectRepository;
import com.software.data.TaskRepository;
import com.software.data.methodology.SprintRepository;
import com.software.domain.Task;
import com.software.domain.methodology.scrum.Sprint;
import com.software.service.exception.project.ProjectNotFoundException;
import com.software.service.exception.task.TaskNotFoundException;
import com.software.service.methodology.DevelopmentImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrumServiceImpl implements DevelopmentImplementation<Sprint> {

    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Sprint saveEntity(Long projectId, Sprint sprint) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        sprint.setProject(project);

        validateEntity(sprint);
        return sprintRepository.save(sprint);
    }

    @Override
    public List<Sprint> findAllEntities(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException(projectId)
        );
        return sprintRepository.findAllByProject(project);
    }

    @Override
    public Sprint getByIdEntity(Long projectId, Long id) {
        return sprintRepository.findByIdAndProjectId(id, projectId)
                .orElseThrow(() -> new IllegalArgumentException("Sprint not found"));
    }

    @Override
    public void deleteEntity(Long projectId, Long id) {
        sprintRepository.deleteById(id);
    }

    @Override
    public void validateEntity(Sprint sprint) {
        if (sprint.getId() == null) {
            throw new IllegalArgumentException("Project doesn't exist.");
        }

        var project = projectRepository.findById(sprint.getProject().getId())
                .orElseThrow(() -> new ProjectNotFoundException(sprint.getProject().getId()));

        for (Task task : sprint.getTasks()) {
            if (taskRepository.findByIdAndProject(task.getId(), project).isEmpty())
                throw new TaskNotFoundException(task.getId());
        }

        for (Task task : sprint.getTasks()) {
            if (task.getTaskStatus() == TaskStatus.IN_PROGRESS || task.getTaskStatus() == TaskStatus.DONE) {
                throw new IllegalArgumentException("Cannot add task with status IN_PROGRESS or DONE to the sprint.");
            }

            if (task.getTaskType() != TaskType.TASK && task.getTaskType() != TaskType.STORY) {
                throw new IllegalArgumentException("Task must be of type TASK or STORY.");
            }

            if (task.getSprint() != null && !task.getSprint().getId().equals(sprint.getId())) {
                throw new IllegalArgumentException("Task " + task.getTitle() + " is already assigned to another sprint.");
            }
        }
    }

    @Override
    public void updateTaskStatus(Long taskId, TaskStatus newStatus) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setTaskStatus(newStatus);
        taskRepository.save(task);
    }

    @Override
    public boolean isEntityAssociatedWithProject(Long projectId, Long sprintId) {
        return false;
    }
}