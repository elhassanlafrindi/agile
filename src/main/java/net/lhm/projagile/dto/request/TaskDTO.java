package net.lhm.projagile.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskDTO {
    @NotBlank
    @Size(max = 100)
    private String title;
    @NotBlank
    @Size(max = 255)
    private String description;

    @NotNull
    private long createdByUserId;
    @NotNull
    private long assignedToUserId;
    @NotNull
    private long userStoryId;
}