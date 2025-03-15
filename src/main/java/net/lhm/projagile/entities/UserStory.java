package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity @Data
@NoArgsConstructor @AllArgsConstructor
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int priorite;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToMany(fetch = FetchType.LAZY)
  /*  @JoinTable(name = "userstory_epic",
            joinColumns = @JoinColumn(name = "userstory_id"),
            inverseJoinColumns = @JoinColumn(name = "epic_id"))*/
    private List<Epic> epics;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductBacklog productBacklog;

    @ManyToOne(fetch = FetchType.LAZY)
    private SprintBacklog sprintBacklog;
}
