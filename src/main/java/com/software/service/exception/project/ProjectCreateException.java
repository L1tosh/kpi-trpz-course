package com.software.service.exception.project;

public class ProjectCreateException extends RuntimeException {
    private static final String PROJECT_CREATE_MESSAGE = "Project with name %s exist";

    public ProjectCreateException(String projectName) {
        super(PROJECT_CREATE_MESSAGE.formatted(projectName));
    }
}
