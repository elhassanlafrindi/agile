package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Epic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpicRepository extends JpaRepository<Epic, Long> {
}