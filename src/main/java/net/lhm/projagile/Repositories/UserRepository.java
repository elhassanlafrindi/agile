package net.lhm.projagile.Repositories;

import net.lhm.projagile.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}