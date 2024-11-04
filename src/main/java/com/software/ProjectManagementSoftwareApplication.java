package com.software;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.software.data")
public class ProjectManagementSoftwareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementSoftwareApplication.class, args);
    }

}
