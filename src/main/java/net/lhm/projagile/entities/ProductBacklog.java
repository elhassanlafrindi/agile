package net.lhm.projagile.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<UserStory> userStories ;
}
