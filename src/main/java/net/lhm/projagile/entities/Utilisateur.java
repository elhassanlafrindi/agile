package net.lhm.projagile.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Utilisateur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String prenom;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

}
