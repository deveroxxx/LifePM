package bakos.life_pm.repository;

import bakos.life_pm.entity.Board;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepository extends ListCrudRepository<Board, UUID> {

    @Query(value = "SELECT COALESCE(MAX(b.position), 0) FROM Board b")
    Integer findMaxBoardPosition();

    Optional<Board> findByIdAndUserName(UUID id, String userName);

    List<Board> findByUserName(String userName);

    default Board findByIdAndUserNameOrThrow(UUID id, String username) {
        return findByIdAndUserName(id, username).
                orElseThrow(() -> new EntityNotFoundException("Board not found with id:" + id));
    }

    default Board findByIdOrThrow(UUID id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Board not found with id:" + id));
    }

}
