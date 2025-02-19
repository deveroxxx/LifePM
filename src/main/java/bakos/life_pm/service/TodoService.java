package bakos.life_pm.service;

import bakos.life_pm.dto.request.PatchTodoRequest;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.enums.Priority;
import bakos.life_pm.enums.TodoType;
import bakos.life_pm.exception.PositionOverflowException;
import bakos.life_pm.repository.BoardColumnRepository;
import bakos.life_pm.repository.TodoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static bakos.life_pm.constant.Constants.SPARSE_POSITION_GAP;

@Service
public class TodoService {

    private final TodoRepository todoRepo;
    private final EntityManager entityManager;
    private final BoardColumnRepository columnRepository;

    public TodoService(TodoRepository todoRepo, EntityManager entityManager, BoardColumnRepository columnRepository) {
        this.todoRepo = todoRepo;
        this.entityManager = entityManager;
        this.columnRepository = columnRepository;
    }

    @Transactional
    public void patchTodo(UUID todoId, PatchTodoRequest request) {
        Todo todo = getTodo(todoId);
        if (request.getTitle() != null && !request.getTitle().isBlank()) {
            todo.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription().isBlank() ? null : request.getDescription());
        }
    }


    public Todo createTodo(String title, UUID columnId) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setPosition(todoRepo.findMaxPositionInColumn(columnId) + SPARSE_POSITION_GAP);
        todo.setPriority(Priority.DEFAULT);
        todo.setTodoType(TodoType.GENERIC);
        BoardColumn column = entityManager.getReference(BoardColumn.class, columnId);
        todo.setBoardColumn(column);
        return todoRepo.save(todo);
    }

    public void deleteTodo(UUID id) {
        todoRepo.deleteById(id);
    }

    public Todo getTodo(UUID id) {
        return todoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo with id " + id + " not found"));

    }

    public List<Todo> listTodos() {
        return todoRepo.findAll();
    }


    @Transactional
    public void updateTodoPosition(UUID movedTodoId, UUID prevTodoId, UUID nextTodoId, UUID newColumnId) {
        Todo movedTodo = getTodo(movedTodoId);
        if (newColumnId != null) {
            movedTodo.setBoardColumn(columnRepository.findByIdOrThrow(newColumnId));
        }
        try {
            int newPosition = Utils.getSparseOrder(
                    prevTodoId == null ? null : getTodo(prevTodoId).getPosition(),
                    nextTodoId == null ? null : getTodo(nextTodoId).getPosition());
            movedTodo.setPosition(newPosition);
        } catch (PositionOverflowException e) {
            recreateAllTodoPositionInColumn(movedTodo);
        }
    }

    private static void recreateAllTodoPositionInColumn(Todo movedTodo) {
        int position = SPARSE_POSITION_GAP;
        for (Todo column : movedTodo.getBoardColumn().getTodos()) {
            column.setPosition(position);
            position += SPARSE_POSITION_GAP;
        }
    }




}
