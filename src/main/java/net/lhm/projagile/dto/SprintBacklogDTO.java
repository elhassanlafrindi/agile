package net.lhm.projagile.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class SprintBacklogDTO {

    @NotNull(message = "Le titre est obligatoire")
    @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
    private String title;
}