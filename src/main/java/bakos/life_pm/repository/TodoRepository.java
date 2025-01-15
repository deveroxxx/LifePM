package bakos.life_pm.repository;

import bakos.life_pm.entity.Todo;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface TodoRepository extends ListCrudRepository<Todo, UUID> {

}
