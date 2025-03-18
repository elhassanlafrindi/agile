package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status statut;
    public enum Status {
        ToDo, InProgress, Done
    }

    @ManyToMany(mappedBy = "epics", fetch = FetchType.LAZY)
    private List<UserStory> userStories ;


}
