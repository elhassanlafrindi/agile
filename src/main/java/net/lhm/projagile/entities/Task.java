package net.lhm.projagile.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity @Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Statut statut;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private UserStory userStory;
}
