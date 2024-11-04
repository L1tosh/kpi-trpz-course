package com.software.service.exception.project;

public class ProjectNotFoundException extends RuntimeException {
    private static final String PROJECT_NOT_FOUND = "Project with id %d not found";

    public ProjectNotFoundException(Long id) {
        super(PROJECT_NOT_FOUND.formatted(id));
    }
}
