package bakos.life_pm.repository;

import bakos.life_pm.entity.BoardColumn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BoardColumnRepository extends ListCrudRepository<BoardColumn, UUID> {

    @Query(value = "SELECT COALESCE(MAX(bc.position), 0) " +
            "FROM BoardColumn bc " +
            "JOIN bc.board b " +
            "WHERE b.id = :boardId")
    Integer findMaxPositionInBoard(@Param("boardId") UUID boardId);
}
