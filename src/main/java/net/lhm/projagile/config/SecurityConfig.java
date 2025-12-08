package net.lhm.projagile.config;

import net.lhm.projagile.entities.User;
import net.lhm.projagile.Repositories.UserRepository;
import net.lhm.projagile.entities.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().permitAll()  // Autorise toutes les requêtes
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
//    @Bean
//    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByEmail("elhassan.lafrindi@gmail.com").isEmpty()) {
//                User user = User.builder()
//                        .firstName("elhassan")
//                        .lastName("lafrindi")
//                        .email("elhassan.lafrindi@gmail.com")
//                        .role(Role.PRODUCT_OWNER)
//                        .isActive(true)
//                        .createdAt(LocalDateTime.now())
//                        .password(passwordEncoder.encode("elhassan.lafrindi@gmail.com"))
//                        .build();
//
//                user.setCreatedBy(user);
//
//                userRepository.save(user);
//            } else {
//                System.out.println("Initial user already exists: elhassan.lafrindi@gmail.com");
//            }
//        };
//    }
}