package net.lhm.projagile.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SprintBacklogDTO {
    @NotNull
    @Size(min = 5, max = 50)
    private String title;
    @NotNull
    private long productBacklogId;
}