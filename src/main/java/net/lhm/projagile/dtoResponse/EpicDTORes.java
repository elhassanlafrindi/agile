package net.lhm.projagile.dtoResponse;


import lombok.*;
import net.lhm.projagile.dto.UserStoryDTO;


import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpicDTORes {

    private int id;
    private String titre;
    private String description;
    List<UserStoryDTO> usetStory;
}
