package com.software.auth.jwt;

import com.software.auth.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

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

    public UserPrincipal extractUserPrincipal(String token) {
        var decodedJWT = jwtDecoder.decode(token);
        return jwtToPrincipalConverter.convert(decodedJWT);
    }

    public UserPrincipal extractUserPrincipalFromContext() {
        var token = extractTokenFromContext();
        return  extractUserPrincipal(token);
    }
}

