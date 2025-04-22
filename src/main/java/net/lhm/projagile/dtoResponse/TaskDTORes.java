package net.lhm.projagile.dtoResponse;

import jakarta.persistence.*;
import lombok.*;
import net.lhm.projagile.dto.SprintBacklogDTO;
import net.lhm.projagile.dto.UserStoryDTO;
import net.lhm.projagile.dto.UtilisateurDTO;
import net.lhm.projagile.entities.Statut;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskDTORes {
    private int id;
    private String titre;
    private String description;
    private Statut statut;
    private SprintBacklogDTO sprintBacklog;
    private UtilisateurDTO user;
    private UserStoryDTO userStory;
}
