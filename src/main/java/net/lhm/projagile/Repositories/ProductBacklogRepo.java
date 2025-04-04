package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductBacklogRepo extends JpaRepository<ProductBacklog, Integer> {

    List<ProductBacklog> findAll();

    // Trouver un ProductBacklog par ID
    Optional<ProductBacklog> findById(int id);

    // Trouver les ProductBacklogs par nom
    List<ProductBacklog> findByNom(String nom);

    // Vérifier si un ProductBacklog existe avec un certain nom
    boolean existsByNom(String nom);
}
