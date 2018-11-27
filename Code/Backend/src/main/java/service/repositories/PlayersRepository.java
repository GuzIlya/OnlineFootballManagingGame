package service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.models.Player;

import java.util.List;

public interface PlayersRepository extends JpaRepository<Player, Long> {
    List<Player> findAllByPosition(String position);
}
