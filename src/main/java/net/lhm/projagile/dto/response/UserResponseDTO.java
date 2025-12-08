package net.lhm.projagile.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import net.lhm.projagile.entities.Role;
@Data
@Builder
public class UserResponseDTO {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 5, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 5, max = 50)
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private boolean isActive;
    @NotNull
    private Role role;
    @NotNull
    private String createdByUser;
}
