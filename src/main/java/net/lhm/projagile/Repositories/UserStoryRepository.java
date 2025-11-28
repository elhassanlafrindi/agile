package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Priority;
import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
    List<UserStory> findAllByPriority(Priority priority);
    List<UserStory> findByStatut(Statut statut);
}
