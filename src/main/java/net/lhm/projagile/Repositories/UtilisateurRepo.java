package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Role;
import net.lhm.projagile.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UtilisateurRepo extends JpaRepository<Utilisateur, Integer> {
    // Find user by name (for login)
    Optional<Utilisateur> findByNom(String nom);

    // Find users by role
    List<Utilisateur> findByRole(Role role);

    // Check if user exists by name
    boolean existsByNom(String nom);

    // Find user by name and password (authentication)
    Optional<Utilisateur> findByNomAndPassword(String nom, String password);

    // Count users by role
    long countByRole(Role role);
}