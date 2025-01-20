package bakos.life_pm.repository;

import bakos.life_pm.entity.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface BoardRepository extends ListCrudRepository<Board, UUID> {

    @Query(value = "SELECT COALESCE(MAX(b.position), 0) FROM Board b")
    Integer findMaxBoardPosition();

}
