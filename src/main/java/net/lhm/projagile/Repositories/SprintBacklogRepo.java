package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.SprintBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintBacklogRepo extends JpaRepository<SprintBacklog, Integer> {

    // Find a SprintBacklog by its ID
    Optional<SprintBacklog> findById(int id);

    // Find all SprintBacklogs
    List<SprintBacklog> findAll();

    // Find a SprintBacklog by title
    Optional<SprintBacklog> findByTitle(String title);

    // Delete a SprintBacklog by its ID
    void deleteById(int id);

    // Find SprintBacklogs that have a specific UserStory associated with them
    List<SprintBacklog> findByUserStories_Id(int userStoryId);
}
