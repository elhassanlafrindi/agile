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
    private Long id;
    private String title;
    private String description;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "epic")
    List<UserStory> userStories;

}
