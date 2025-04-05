package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EpicRepo extends JpaRepository<Epic, Integer> {

    List<Epic> findByStatut(Statut statut);

    // Find epics containing a specific term in name or description
    List<Epic> findByNomContainingOrDescriptionContaining(String nomTerm, String descriptionTerm);

    // Find epics associated with a specific user story
    @Query("SELECT e FROM Epic e WHERE :userStoryId IN (SELECT u.id FROM e.userStories u)")
    List<Epic> findByUserStoryId(@Param("userStoryId") int userStoryId);

    // Find epics with no user stories assigned
    @Query("SELECT e FROM Epic e WHERE e.userStories IS EMPTY")
    List<Epic> findEpicsWithNoUserStories();

    // Count epics by status
    long countByStatut(Statut statut);
}
