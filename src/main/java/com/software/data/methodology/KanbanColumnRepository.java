package com.software.data.methodology;

import com.software.domain.methodology.kanban.KanbanColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanColumnRepository extends JpaRepository<KanbanColumn, Long> {
}
