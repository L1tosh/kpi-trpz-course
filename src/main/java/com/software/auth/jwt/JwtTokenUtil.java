package com.software.auth.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class JwtTokenUtil {
    public String extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new AccessDeniedException("Invalid or missing Authorization header");
        }
        return token.substring(7);
    }

    public String extractTokenFromContext() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No request attributes available. Ensure that this method is called within an HTTP request context.");
        }
        return extractTokenFromRequest(attributes.getRequest());
    }
}
