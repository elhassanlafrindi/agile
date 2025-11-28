package net.lhm.projagile.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductBacklogDTO {
    @NotNull
    @Size(min = 5, max = 50)
    private String title;
}