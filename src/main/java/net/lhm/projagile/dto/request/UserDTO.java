package net.lhm.projagile.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import net.lhm.projagile.entities.Role;

import java.time.LocalDateTime;
@Builder
@Data
public class UserDTO {
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private Role role;
    @NotNull
    private Long createdBy;
}