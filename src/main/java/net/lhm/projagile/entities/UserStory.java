package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
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
    private int priorite;
    @Enumerated(EnumType.STRING)
    private Statut statut;


    @ManyToMany(fetch = FetchType.EAGER)
    private List<Epic> epics;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductBacklog productBacklog;

    @ManyToOne(fetch = FetchType.LAZY)
    private SprintBacklog sprintBacklog;

    @OneToMany(mappedBy = "userStory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Task> task=new HashSet<>();
}
