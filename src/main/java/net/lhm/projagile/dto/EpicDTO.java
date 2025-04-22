package net.lhm.projagile.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpicDTO {
    @NotNull(message = "Le titre est obligatoire")
    @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères")
    private String titre;
    int id;
    @Size(min = 2, message = "La discription doit contenir au moins 10 caractères")
    private String description;

}
