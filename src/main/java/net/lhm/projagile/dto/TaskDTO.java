package net.lhm.projagile.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import net.lhm.projagile.entities.Statut;
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class TaskDTO {
    @NotNull(message = "Le titre est obligatoire")
    @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères")
    private String titre;
    private String description;
    int id;
    @NotNull(message = "Le statut est obligatoire")
    private Statut statut;
}
