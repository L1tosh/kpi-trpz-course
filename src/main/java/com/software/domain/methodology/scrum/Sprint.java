package com.software.domain.methodology.scrum;

import com.software.domain.Project;
import com.software.domain.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sprints")
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ElementCollection
    @CollectionTable(
            name = "user_sprint",
            joinColumns = @JoinColumn(name = "sprint_id")
    )
    @Column(name = "user_id")
    private List<Long> command;

    @OneToMany(mappedBy = "sprint")
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
