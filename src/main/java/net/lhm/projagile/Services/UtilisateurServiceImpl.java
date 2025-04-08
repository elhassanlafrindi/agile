package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.UtilisateurRepo;
import net.lhm.projagile.dto.UtilisateurDTO;
import net.lhm.projagile.entities.Role;
import net.lhm.projagile.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final UtilisateurRepo utilisateurRepo;
    public UtilisateurServiceImpl(UtilisateurRepo utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
    @Transactional
    public Utilisateur createUtilisateur(UtilisateurDTO utilisateurDTO) {
        utilisateurDTO.setPassword(passwordEncoder.encode(utilisateurDTO.getPassword()));
        return utilisateurRepo.save(Utilisateur.builder()
                .nom(utilisateurDTO.getNom())
                .prenom(utilisateurDTO.getPrenom())
                .password(utilisateurDTO.getPassword()) // À crypter en production
                .email(utilisateurDTO.getEmail())
                .role(utilisateurDTO.getRole())
                .build());
    }

    @Override
    public Utilisateur updateUtilisateur(Integer id, UtilisateurDTO utilisateurDTO) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepo.findById(id);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            utilisateur.setNom(utilisateurDTO.getNom());
            utilisateur.setPrenom(utilisateurDTO.getPrenom());
            utilisateur.setPassword(utilisateurDTO.getPassword()); // À crypter
            utilisateur.setEmail(utilisateurDTO.getEmail());
            utilisateur.setRole(utilisateurDTO.getRole());
            return utilisateurRepo.save(utilisateur);
        }
        throw new RuntimeException("Utilisateur non trouvé !");
    }

    @Override
    public void deleteUtilisateur(Integer id) {
        if (!utilisateurRepo.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé !");
        }
        utilisateurRepo.deleteById(id);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepo.findAll();
    }

    @Override
    public Utilisateur getUtilisateurById(Integer id) {
        return utilisateurRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé !"));
    }

    @Override
    public Utilisateur loadUtilisateurByEmail(String email) {
        Optional<Utilisateur> user=utilisateurRepo.findByEmail(email);
        if(user.isPresent()) {
            Utilisateur utilisateur=user.get();
            return utilisateur;
        }
        throw new RuntimeException("Utilisateur non trouvé !");
    }

    @Override
    public List<Utilisateur> loadUtilisateurByRole(Role role) {
        return utilisateurRepo.findByRole(role);
    }
}
