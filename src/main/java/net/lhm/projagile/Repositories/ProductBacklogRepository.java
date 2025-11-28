package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBacklogRepository extends JpaRepository<ProductBacklog, Long> {
}