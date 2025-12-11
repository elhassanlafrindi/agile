package net.lhm.projagile.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDTO {

@NotNull
    private  long id;
@NotNull
    private  String email;
@NotNull
    private String firstName;
@NotNull
    private String lastName;
@NotNull
    private String token;
}
