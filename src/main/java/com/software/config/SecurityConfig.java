package com.software.config;

import com.software.auth.UnauthorizedHandler;
import com.software.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UnauthorizedHandler unauthorizedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

                .formLogin(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(handler -> handler
                        .authenticationEntryPoint(unauthorizedHandler))

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/projects").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/comments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/projects/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/roles").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/roles/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/projects/{projectId}/workers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/projects/{projectId}/workers/{userId}").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}


