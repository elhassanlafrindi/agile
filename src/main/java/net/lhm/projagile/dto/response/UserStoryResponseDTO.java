package net.lhm.projagile.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import net.lhm.projagile.entities.Priority;
import net.lhm.projagile.entities.Statut;
@Builder
@Data
public class UserStoryResponseDTO {
    @NotNull(message = "Id is mandatory")
    private Long id;
    
    @NotBlank(message = "Title is mandatory")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;
    
    @NotBlank(message = "Description is mandatory")
    @Size(min = 5, max = 50, message = "Description must be between 5 and 50 characters")
    private String description;
    
    @NotNull(message = "Priority is mandatory")
    private Priority priority;
    
    private Statut statut;
    

}
