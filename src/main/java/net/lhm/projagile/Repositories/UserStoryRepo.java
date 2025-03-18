package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryRepo extends JpaRepository<UserStory, Integer> {
}
