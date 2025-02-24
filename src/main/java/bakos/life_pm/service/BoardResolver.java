package bakos.life_pm.service;

import bakos.life_pm.entity.*;
import bakos.life_pm.repository.TodoRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BoardResolver {

    private final TodoRepository todoRepository;

    private final EntityManager entityManager;


    public BoardResolver(TodoRepository todoRepo, EntityManager entityManager) {
        this.todoRepository = todoRepo;
        this.entityManager = entityManager;
    }

    @Transactional
    public Board getBoard(UUID id, Class<? extends CustomerRelated> entityClass){
        if (entityClass == Todo.class) {
            return entityManager.find(BoardColumn.class, id).getBoard();
        }
        if (entityClass == BoardColumn.class) {
            return entityManager.find(BoardColumn.class, id).getBoard();
        }
        if (entityClass == Board.class) {
            return entityManager.find(Board.class, id);
        }
        throw new AssertionError("Unsupported entity class: " + entityClass);
    }

}
