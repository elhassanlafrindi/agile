package net.lhm.projagile.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Builder
@Data
public class ProductBacklogResponseDTO {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 5, max = 50)
    private String title;
    @NotNull
    private Date dateCreated;
}
