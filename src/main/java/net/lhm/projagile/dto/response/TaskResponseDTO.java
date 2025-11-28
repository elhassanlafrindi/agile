package net.lhm.projagile.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskResponseDTO {
    @NotNull
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    @Size(max = 255)
    private String description;

    @NotNull
    private String emailUserCreatedBy;
    @NotNull
    private String emailUserAssignedTo;
    @NotNull
    private Long userStoryId;
}