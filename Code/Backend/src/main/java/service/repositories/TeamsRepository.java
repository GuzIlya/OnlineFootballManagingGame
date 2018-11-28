package service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.models.UsersTeam;
import service.models.User;

import java.util.Optional;

public interface TeamsRepository extends JpaRepository<UsersTeam, Long> {
    Optional<UsersTeam> findByOwner(User owner);
}
