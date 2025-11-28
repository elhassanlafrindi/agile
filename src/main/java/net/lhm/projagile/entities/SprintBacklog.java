package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SprintBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Date dateCreated;
    private Date dateEnd;

    @OneToMany(mappedBy = "sprintBacklog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserStory> userStories ;

    @ManyToOne
    ProductBacklog productBacklog;
}
