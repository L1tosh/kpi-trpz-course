package com.software.service.methodology.implementor;

import com.software.common.task.TaskStatus;
import com.software.common.task.TaskType;
import com.software.data.ProjectRepository;
import com.software.data.TaskRepository;
import com.software.data.methodology.KanbanBoardRepository;
import com.software.domain.Task;
import com.software.domain.methodology.kanban.KanbanBoard;
import com.software.domain.methodology.kanban.KanbanColumn;
import com.software.service.exception.project.ProjectNotFoundException;
import com.software.service.methodology.DevelopmentImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KanbanServiceImpl implements DevelopmentImplementation<KanbanBoard> {

    private final KanbanBoardRepository kanbanBoardRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    public KanbanBoard saveEntity(Long projectId, KanbanBoard board) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));

        board.setProject(project);

        validateEntity(board);
        return kanbanBoardRepository.save(board);
    }

    @Override
    public List<KanbanBoard> findAllEntities(Long projectId) {
        var project = projectRepository.findById(projectId).orElseThrow(
                () -> new ProjectNotFoundException(projectId)
        );
        return kanbanBoardRepository.findAllByProject(project);
    }

    @Override
    public KanbanBoard getByIdEntity(Long projectId, Long id) {
        return kanbanBoardRepository.findByIdAndProjectId(id, projectId)
                .orElseThrow(() -> new IllegalArgumentException("Kanban board not found"));
    }

    @Override
    public void deleteEntity(Long projectId, Long id) {
        kanbanBoardRepository.deleteById(id);
    }

    @Override
    public void validateEntity(KanbanBoard board) {
        if (board.getProject() == null) {
            throw new IllegalArgumentException("Kanban board must be assigned to a project.");
        }

        for (KanbanColumn column : board.getColumns()) {
            for (Task task : column.getTasks()) {
                if (task.getTaskStatus() == TaskStatus.IN_PROGRESS || task.getTaskStatus() == TaskStatus.DONE) {
                    throw new IllegalArgumentException("Cannot add task with status IN_PROGRESS or DONE to the Kanban board.");
                }

                if (task.getTaskType() != TaskType.TASK && task.getTaskType() != TaskType.STORY) {
                    throw new IllegalArgumentException("Task must be of type TASK or STORY.");
                }
            }
        }
    }

    @Override
    public void updateTaskStatus(Long taskId, TaskStatus newStatus) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setTaskStatus(newStatus);
        taskRepository.save(task);
    }

    @Override
    public boolean isEntityAssociatedWithProject(Long projectId, Long entityId) {
        return kanbanBoardRepository.existsByIdAndProjectId(entityId, projectId);
    }
}