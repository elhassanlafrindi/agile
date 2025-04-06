package net.lhm.projagile.Services;

import net.lhm.projagile.dto.UtilisateurDTO;
import net.lhm.projagile.entities.Utilisateur;
import java.util.List;

public interface UtilisateurService {
    Utilisateur createUtilisateur(UtilisateurDTO utilisateurDTO);
    Utilisateur updateUtilisateur(Integer id, UtilisateurDTO utilisateurDTO);
    void deleteUtilisateur(Integer id);
    List<Utilisateur> getAllUtilisateurs();
    Utilisateur getUtilisateurById(Integer id);
}
