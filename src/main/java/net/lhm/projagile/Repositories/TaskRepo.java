package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByUserStory(UserStory userStory);
    List<Task> findBySprintBacklog(SprintBacklog sprintBacklog);
    List<Task> findByUser(Utilisateur user);
    List<Task> findByStatut(Statut statut);
    List<Task> findByTitre(String titre);
}
