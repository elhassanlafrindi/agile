package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.Epic;
import net.lhm.projagile.entities.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EpicRepo extends JpaRepository<Epic, Integer> {



}
