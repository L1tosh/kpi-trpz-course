package com.software.data;

import com.software.domain.user.UserProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRoleRepository extends JpaRepository<UserProjectRole, Long> {
    List<UserProjectRole> findByProjectId(Long projectId);
    List<UserProjectRole> findByUserIdAndProjectId(Long userId, Long projectId);
}
