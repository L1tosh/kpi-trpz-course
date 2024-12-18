package com.software.data;

import com.software.domain.Project;
import com.software.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
    Optional<Task> findByIdAndProject(Long taskId, Project project);
}
