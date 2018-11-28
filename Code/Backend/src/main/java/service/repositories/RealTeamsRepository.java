package service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.models.RealTeam;

import java.util.Optional;

public interface RealTeamsRepository extends JpaRepository<RealTeam, Long> {
    Optional<RealTeam> findByTeamName(String teamName);
}
