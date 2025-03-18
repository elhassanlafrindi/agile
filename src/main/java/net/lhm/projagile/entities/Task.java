package net.lhm.projagile.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String description;


    @Enumerated(EnumType.STRING)
    private Status statut;
    public enum Status {
        ToDo, InProgress, Done
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private SprintBacklog sprintBacklog;

    @OneToOne(fetch = FetchType.EAGER)
    private UserStory userStory;
}
