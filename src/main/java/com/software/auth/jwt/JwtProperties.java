package com.software.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Setter
@Configuration
public class JwtProperties {
    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.lifetime}")
    private Duration lifetime;
}
