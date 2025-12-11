package net.lhm.projagile.Controllers;


import jakarta.validation.Valid;
import lombok.Value;
import net.lhm.projagile.Services.AuthenticationService;
import net.lhm.projagile.dto.request.AuthDTO;
import net.lhm.projagile.dto.response.AuthResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@Valid @RequestBody AuthDTO authDTO){

        return ResponseEntity.ok(authenticationService.authenticate(authDTO));
    }
}
