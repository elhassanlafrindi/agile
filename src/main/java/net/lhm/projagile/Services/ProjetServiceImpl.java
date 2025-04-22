package net.lhm.projagile.Services;

import jakarta.transaction.Transactional;
import net.lhm.projagile.Repositories.ProductBacklogRepo;
import net.lhm.projagile.Repositories.ProjetRepo;
import net.lhm.projagile.dto.ProjetDTO;
import net.lhm.projagile.entities.ProductBacklog;
import net.lhm.projagile.entities.Projet;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjetServiceImpl implements ProjetService {
    private final ProjetRepo projetRepo;
    ProductBacklogRepo productBacklogRepo;
    public ProjetServiceImpl(ProjetRepo projetRepo, ProductBacklogRepo productBacklogRepo) {
        this.projetRepo = projetRepo;
        this.productBacklogRepo=productBacklogRepo;
    }

    @Override
    @Transactional
    public Projet createProjet(ProjetDTO projetDTO) {
        return projetRepo.save(Projet.builder()
                .title(projetDTO.getTitle())
                .build());
    }

    @Override
    public Projet updateProjet(Integer id, ProjetDTO projetDTO) {
        Optional<Projet> projetOptional = projetRepo.findById(id);
        if (projetOptional.isPresent()) {
            Projet projet = projetOptional.get();
            projet.setTitle(projetDTO.getTitle());
            return projetRepo.save(projet);
        }
        throw new RuntimeException("Projet non trouvé !");
    }

    @Override
    public void deleteProjet(Integer id) {
        if (!projetRepo.existsById(id)) {
            throw new RuntimeException("Projet non trouvé !");
        }
        projetRepo.deleteById(id);
    }

    @Override
    public List<Projet> getAllProjets() {
        return projetRepo.findAll();
    }

    @Override
    public Projet getProjetById(Integer id) {
        return projetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé !"));
    }
    @Override
    public Projet affectProductbacklog(int idBacklog, int idProjet) {
        Optional<Projet> projetOptional = projetRepo.findById(idProjet);
        if (projetOptional.isEmpty()) {
            throw new RuntimeException("Projet avec l'ID " + idProjet + " introuvable !");
        }

        Optional<ProductBacklog> productBacklog = productBacklogRepo.findById(idBacklog);
        if (productBacklog.isEmpty()) {
            throw new RuntimeException("ProductBacklog avec l'ID " + idBacklog + " introuvable !");
        }

        Projet projet = projetOptional.get();
        projet.setProductBacklog(productBacklog.get());
        projetRepo.save(projet);
        return projet;
    }

}
