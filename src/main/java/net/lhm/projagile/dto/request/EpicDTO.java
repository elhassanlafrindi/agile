package net.lhm.projagile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EpicDTO {
    @NotBlank
    @Size(min = 5, max = 50)
    private String title;
    @NotBlank
    @Size(min = 5, max = 50)
    private String description;
    
    @NotNull
    private Long productBacklogId;
}