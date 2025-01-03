package bakos.life_pm.service;


import bakos.life_pm.dto.BoardDto;
import bakos.life_pm.dto.TodoDto;
import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.mapper.BoardMapper;
import bakos.life_pm.mapper.TodoMapper;
import bakos.life_pm.repository.BoardRepository;
import bakos.life_pm.repository.TodoRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

//TODO: split it later if it gets big
@Service
public class CoreService {

    private final BoardRepository boardRepo;
    private final EntityManager entityManager;
    private final TodoRepository todoRepository;

    public CoreService(BoardRepository boardRepo, EntityManager entityManager, TodoRepository todoRepository) {
        this.boardRepo = boardRepo;
        this.entityManager = entityManager;
        this.todoRepository = todoRepository;
    }

    public BoardDto createBoard(String title, String description) {
        Board board = new Board(title, description);
        board = this.boardRepo.save(board);
        return BoardMapper.INSTANCE.toDto(board);
    }

    public TodoDto createTodo(String title, String description, UUID columnId) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        if (columnId != null) {
            BoardColumn column = entityManager.getReference(BoardColumn.class, columnId);
            todo.setBoardColumn(column);
        }
        todo = todoRepository.save(todo);
        return TodoMapper.INSTANCE.toDto(todo);
    }


}
