package bakos.life_pm.service;

import bakos.life_pm.entity.*;
import bakos.life_pm.repository.TodoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserNameResolver {

    private final TodoRepository todoRepository;

    private final EntityManager entityManager;


    public UserNameResolver(TodoRepository todoRepo, EntityManager entityManager) {
        this.todoRepository = todoRepo;
        this.entityManager = entityManager;
    }

    @Transactional
    public String getUserName(UUID id, Class<? extends CustomerRelated> entityClass){
        if (entityClass == Todo.class) {
            return todoRepository.findUserNameByTodoId(id);
        }
        if (entityClass == BoardColumn.class) {
            return entityManager.find(BoardColumn.class, id).getBoard().getUserName();
        }
        if (entityClass == Board.class) {
            return entityManager.find(Board.class, id).getUserName();
        }
        if (entityClass == Comment.class) {
            return entityManager.find(Comment.class, id).getUserName();
        }
        throw new AssertionError("Unsupported entity class: " + entityClass);
    }

}
