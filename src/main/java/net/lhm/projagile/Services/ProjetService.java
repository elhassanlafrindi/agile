package net.lhm.projagile.Services;

import net.lhm.projagile.dto.ProjetDTO;
import net.lhm.projagile.entities.Projet;
import java.util.List;

public interface ProjetService {
    Projet createProjet(ProjetDTO projetDTO);
    Projet updateProjet(Integer id, ProjetDTO projetDTO);
    void deleteProjet(Integer id);
    List<Projet> getAllProjets();
    Projet getProjetById(Integer id);
}
