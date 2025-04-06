package net.lhm.projagile.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpicDTO {
    @NotNull(message = "Le titre est obligatoire")
    @Size(min = 2, message = "Le titre doit contenir au moins 2 caractères")
    private String titre;
    @Size(min = 2, message = "La discription doit contenir au moins 10 caractères")
    private String description;

}
