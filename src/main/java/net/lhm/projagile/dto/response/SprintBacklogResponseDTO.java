package net.lhm.projagile.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class SprintBacklogResponseDTO {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private Date dateCreated;
    @NotNull
    private Date dateEnd;
}