package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity @Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int priorite;
    @Enumerated(EnumType.STRING)
    private Status statut;
    public enum Status {
        ToDo, InProgress, Done
    }

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
