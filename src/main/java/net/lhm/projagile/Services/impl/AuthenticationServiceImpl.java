package net.lhm.projagile.Services.impl;

import net.lhm.projagile.Services.AuthenticationService;
import net.lhm.projagile.dto.request.AuthDTO;
import net.lhm.projagile.dto.response.AuthResponseDTO;
import net.lhm.projagile.security.JwtTokenProvider;
import net.lhm.projagile.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponseDTO authenticate(AuthDTO authDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String token =jwtTokenProvider.generateToken(authentication);
        AuthResponseDTO authResponseDTO=AuthResponseDTO.builder()
                .id(userDetails.getId())
                .email(userDetails.getUsername())
                .firstName(userDetails.getUsername())
                .lastName(userDetails.getUsername())
                .token(token)
                .build();

        return authResponseDTO;
    }


}
