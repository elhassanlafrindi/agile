package net.lhm.projagile.dtoResponse;



import lombok.*;
import net.lhm.projagile.dto.EpicDTO;
import net.lhm.projagile.dto.ProductBacklogDTO;
import net.lhm.projagile.dto.SprintBacklogDTO;
import net.lhm.projagile.dto.TaskDTO;
import net.lhm.projagile.entities.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserStoryDTORes {

    private int id;
    private String title;
    private String description;
    private int priorite;
    private Statut statut;
    private List<EpicDTO> epics;
    private ProductBacklogDTO productBacklog;
   private SprintBacklogDTO sprintBacklog;
   private Set<TaskDTO> task=new HashSet<>();
}
