package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.SprintBacklog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintBacklogRepository extends JpaRepository<SprintBacklog, Long> {
}