package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;

    @Enumerated(EnumType.STRING)
    private Statut statut;


    @ManyToMany(mappedBy = "epics", fetch = FetchType.LAZY)
    private List<UserStory> userStories ;


}
