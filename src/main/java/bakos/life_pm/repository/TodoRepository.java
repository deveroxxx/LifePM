package bakos.life_pm.repository;

import bakos.life_pm.entity.Todo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TodoRepository extends ListCrudRepository<Todo, UUID> {

    @Query(value = "SELECT COALESCE(MAX(t.position), 0) " +
            "FROM Todo t " +
            "JOIN t.boardColumn c " +
            "WHERE c.id = :columnId")
    Integer findMaxPositionInColumn(@Param("columnId") UUID columnId);


}
