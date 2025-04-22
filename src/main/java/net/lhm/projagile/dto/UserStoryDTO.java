package net.lhm.projagile.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import net.lhm.projagile.entities.Statut;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStoryDTO {
    @NotNull(message = "Le titre est obligatoire")
    @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères")
    private String title;
    int id;
    private String description;

    @Min(value = 1, message = "La priorité doit être supérieure ou égale à 1")
    private int priorite;

    @NotNull(message = "Le statut est obligatoire")
    private Statut statut;
}
