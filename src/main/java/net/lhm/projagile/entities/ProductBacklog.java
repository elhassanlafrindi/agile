package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class ProductBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    @OneToMany(mappedBy ="productBacklog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserStory> userStories ;
}
