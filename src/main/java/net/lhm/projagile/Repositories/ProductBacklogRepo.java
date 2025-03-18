package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBacklogRepo extends JpaRepository<ProductBacklog, Integer> {
}
