package service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);
    List<User> findAllByOrderByPointsDesc();
}
