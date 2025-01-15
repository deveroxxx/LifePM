package bakos.life_pm.repository;

import bakos.life_pm.entity.Board;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface BoardRepository extends ListCrudRepository<Board, UUID> {

}
