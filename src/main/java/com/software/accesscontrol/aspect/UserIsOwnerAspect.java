package com.software.accesscontrol.aspect;

import com.software.auth.jwt.JwtTokenUtil;
import com.software.service.ProjectService;
import com.software.service.impl.ProjectServiceImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserIsOwnerAspect {

    private final JwtTokenUtil jwtTokenUtil;
    private final ProjectService projectService;

    @Autowired
    public UserIsOwnerAspect(JwtTokenUtil jwtTokenUtil, ProjectServiceImpl projectService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.projectService = projectService;
    }

    @Before("@annotation(com.software.accesscontrol.annotation.CheckUserIsOwner) && args(projectId,..)")
    public void checkUserInProject(JoinPoint joinPoint, Long projectId) {
        var userPrincipal = jwtTokenUtil.extractUserPrincipalFromContext();

        var project = projectService.getProjectById(projectId);

        if (!project.getOwner().equals(userPrincipal.getUserId())) {
            throw new AccessDeniedException("User is not part of the project");
        }
    }
}
