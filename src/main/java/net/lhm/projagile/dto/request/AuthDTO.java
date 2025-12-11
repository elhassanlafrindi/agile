package net.lhm.projagile.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
