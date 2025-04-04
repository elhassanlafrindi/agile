package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProjetRepo extends JpaRepository<Projet, Integer> {
    Optional<Projet> findByTitle(String title);

    // Find projects containing a specific title (partial match)
    List<Projet> findByTitleContaining(String keyword);

    // Find project by associated product backlog ID
    Optional<Projet> findByProductBacklogId(int productBacklogId);

    // Check if project exists by title
    boolean existsByTitle(String title);

    // Find all projects ordered by title
    List<Projet> findAllByOrderByTitleAsc();
}
