package service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import service.models.MatchResult;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {
}
