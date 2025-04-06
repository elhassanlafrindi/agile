package net.lhm.projagile.entities;

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
    private String password;
    private String prenom;
    private String email;
    private Role role;

}
