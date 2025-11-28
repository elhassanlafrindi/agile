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

    // The user who created the task
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    // The user assigned to execute the task
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_user_id")
    private User assignedTo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_story_id")
    private UserStory userStory;
}