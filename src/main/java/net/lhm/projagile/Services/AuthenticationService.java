package net.lhm.projagile.Services;

import net.lhm.projagile.dto.request.AuthDTO;
import net.lhm.projagile.dto.response.AuthResponseDTO;

public interface AuthenticationService {
    AuthResponseDTO authenticate(AuthDTO authDTO);

}
