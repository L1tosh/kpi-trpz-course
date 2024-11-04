package com.software.domain;

import com.software.domain.item.Item;
import com.software.domain.user.User;
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

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "project")
    private List<User> workers;

    @OneToMany(mappedBy = "project")
    private List<Event> events;

    @OneToMany(mappedBy = "project")
    private List<Item> items;

    @OneToMany(mappedBy = "project")
    private List<Sprint> sprints;
}
