package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity @Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Statut statut;
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(fetch = FetchType.EAGER)
    private Epic epic;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductBacklog productBacklog;

    @ManyToOne(fetch = FetchType.LAZY)
    private SprintBacklog sprintBacklog;

    @OneToMany(mappedBy = "userStory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Task> task=new HashSet<>();
}
