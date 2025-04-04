package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Status;
import net.lhm.projagile.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepo extends JpaRepository<UserStory, Integer> {

    List<UserStory> findAll();

    List<UserStory> findByTitle(String title);

    List<UserStory> findByDescription(String description);

    List<UserStory> findByPriorite(int priorite);

    List<UserStory> findByStatut(Status statut);

    List<UserStory> findByProductBacklogId(int productBacklogId);

    List<UserStory> findBySprintBacklogId(int sprintBacklogId);

    boolean existsByTitle(String title);

    void deleteById(int id);
}

