package net.lhm.projagile.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class EpicResponseDTO {
    @NotBlank
    @Size(min = 5, max = 50)
    private String title;
    @NotBlank
    @Size(min = 5, max = 50)
    private String description;
    private List<UserStoryResponseDTO> userStories;
}
