package com.software.data.methodology;

import com.software.domain.Project;
import com.software.domain.methodology.kanban.KanbanBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KanbanBoardRepository extends JpaRepository<KanbanBoard, Long> {
    Optional<KanbanBoard> findByIdAndProjectId(Long id, Long projectId);
    boolean existsByIdAndProjectId(Long id, Long projectId);
    List<KanbanBoard> findAllByProject(Project project);
}
