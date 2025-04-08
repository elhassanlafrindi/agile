package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    @OneToMany(mappedBy ="productBacklog", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<UserStory> userStories ;
}
