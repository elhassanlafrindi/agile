package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Statut;
import net.lhm.projagile.entities.Task;
import net.lhm.projagile.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByAssignedTo(User assignedTo, Pageable pageable);

    Page<Task> findByCreatedBy(User createdBy, Pageable pageable);

    Page<Task> findByStatut(Statut statut, Pageable pageable);
}