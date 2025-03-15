package net.lhm.projagile.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;

    @OneToMany(mappedBy = "sprintBacklog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserStory> userStories ;

    @OneToMany(mappedBy = "sprintBacklog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;
}
