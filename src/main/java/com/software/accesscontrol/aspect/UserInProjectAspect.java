package com.software.accesscontrol.aspect;

import com.software.auth.jwt.JwtTokenUtil;
import com.software.service.impl.ProjectServiceImpl;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserInProjectAspect {

    private final JwtTokenUtil jwtTokenUtil;
    private final ProjectServiceImpl projectService;

    @Before("@annotation(com.software.accesscontrol.annotation.CheckUserInProject) && args(projectId,..)")
    public void checkUserInProject(JoinPoint joinPoint, Long projectId) {
        var userPrincipal = jwtTokenUtil.extractUserPrincipalFromContext();

        boolean isUserInProject = projectService.isUserInProject(projectId, userPrincipal.getUserId());

        if (!isUserInProject) {
            throw new AccessDeniedException("User is not part of the project");
        }
    }
}
