package com.software.aspect;

import com.software.auth.jwt.JwtDecoder;
import com.software.auth.jwt.JwtToPrincipalConverter;
import com.software.auth.jwt.JwtTokenUtil;
import com.software.service.ProjectService;
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
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    private final ProjectService projectService;

    @Before("@annotation(com.software.annotation.CheckUserInProject) && args(projectId,..)")
    public void checkUserInProject(JoinPoint joinPoint, Long projectId) {
        String token = jwtTokenUtil.extractTokenFromContext();

        var convertJwt = jwtToPrincipalConverter.convert(jwtDecoder.decode(token));
        var userId = convertJwt.getUserId();

        boolean isUserInProject = projectService.isUserInProject(projectId, userId);

        if (!isUserInProject) {
            throw new AccessDeniedException("User is not part of the project");
        }
    }
}
