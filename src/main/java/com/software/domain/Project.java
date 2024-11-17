package com.software.domain;

import com.software.domain.methodology.scrum.Sprint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_id")
    private Long owner;

    @ElementCollection
    @CollectionTable(
            name = "user_project_role",
            joinColumns = @JoinColumn(name = "project_id")
    )
    @Column(name = "user_id")
    private List<Long> workers;

    @OneToMany(mappedBy = "project")
    private List<Event> events;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @OneToMany(mappedBy = "project")
    private List<Sprint> sprints;
}
