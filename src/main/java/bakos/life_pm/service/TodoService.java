package bakos.life_pm.service;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.enums.Priority;
import bakos.life_pm.enums.TodoType;
import bakos.life_pm.repository.TodoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepo;
    private final EntityManager entityManager;

    public TodoService(TodoRepository todoRepo, EntityManager entityManager) {
        this.todoRepo = todoRepo;
        this.entityManager = entityManager;
    }



    public Todo createTodo(String title, UUID columnId) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setPosition(todoRepo.findMaxPositionInColumn(columnId));
        todo.setPriority(Priority.DEFAULT);
        todo.setTodoType(TodoType.GENERIC);
        BoardColumn column = entityManager.getReference(BoardColumn.class, columnId);
        todo.setBoardColumn(column);
        return todoRepo.save(todo);
    }

    public Todo getTodo(UUID id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo with id " + id + " not found"));

    }

    public List<Todo> listTodos() {
        return todoRepo.findAll();
    }



}
