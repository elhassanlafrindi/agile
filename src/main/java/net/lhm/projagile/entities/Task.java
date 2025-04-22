package net.lhm.projagile.entities;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity @Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToOne(fetch = FetchType.EAGER)
    private SprintBacklog sprintBacklog;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", unique = true)
    private Utilisateur user;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserStory userStory;
}
