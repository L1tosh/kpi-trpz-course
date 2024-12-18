package com.software.data.methodology;

import com.software.domain.Project;
import com.software.domain.methodology.scrum.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
    Optional<Sprint> findByIdAndProjectId(Long id, Long projectId);
    boolean existsByIdAndProject(Long id, Project project);
    List<Sprint> findAllByProject(Project project);
}
