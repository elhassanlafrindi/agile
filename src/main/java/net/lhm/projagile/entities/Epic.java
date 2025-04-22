package net.lhm.projagile.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String titre;
    private String description;
  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "epics")
    List<UserStory> usetStory;

}
