package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}