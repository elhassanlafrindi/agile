package net.lhm.projagile;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjAgileApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ProjAgileApplication.class, args);
    }
@Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
}
}
