package bakos.life_pm.service;


import bakos.life_pm.entity.Board;
import bakos.life_pm.repository.BoardRepository;
import bakos.life_pm.repository.TodoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

//TODO: split it later if it gets big
@Service
public class BoardService {

    private final BoardRepository boardRepo;
    private final EntityManager entityManager;
    private final TodoRepository todoRepository;

    public BoardService(BoardRepository boardRepo, EntityManager entityManager, TodoRepository todoRepository) {
        this.boardRepo = boardRepo;
        this.entityManager = entityManager;
        this.todoRepository = todoRepository;
    }

    public Board createBoard(String name) {
        Board board = new Board(name);
        board.setPosition(boardRepo.findMaxBoardPosition() + 1);
        board = this.boardRepo.save(board);
        return board;
    }

    public Board getBoard(UUID id) {
        return boardRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Board with id " + id + " not found"));

    }

    public List<Board> listBoards() {
        return boardRepo.findAll();
    }






}
