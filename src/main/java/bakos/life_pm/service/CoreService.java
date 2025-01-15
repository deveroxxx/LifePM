package bakos.life_pm.service;


import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.exception.EntityNotFoundException;
import bakos.life_pm.repository.BoardRepository;
import bakos.life_pm.repository.TodoRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Board createBoard(String title, String description) {
        Board board = new Board(title, description);
        board = this.boardRepo.save(board);
        return board;
    }

    public Board getBoard(UUID id) {
        Optional<Board> board = boardRepo.findById(id);
        if (board.isPresent()) {
            return board.get();
        } else {
            throw new EntityNotFoundException("Board with id " + id + " not found");
        }
    }

    public List<Board> listBoards() {
        return boardRepo.findAll();
    }

    public Todo createTodo(String title, String description, UUID columnId) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        if (columnId != null) {
            BoardColumn column = entityManager.getReference(BoardColumn.class, columnId);
            todo.setBoardColumn(column);
        }
        return todoRepository.save(todo);
    }


}
