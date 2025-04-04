package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.SprintBacklog;
import net.lhm.projagile.entities.Status;
import net.lhm.projagile.entities.Task;
import net.lhm.projagile.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
    List<Task> findByUserStory(UserStory userStory);
    List<Task> findBySprintBacklog(SprintBacklog sprintBacklog);

    List<Task> findByStatut(Status statut);
    List<Task> findByTitre(String titre);
}
