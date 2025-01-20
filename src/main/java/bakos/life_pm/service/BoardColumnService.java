package bakos.life_pm.service;

import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.repository.BoardColumnRepository;
import bakos.life_pm.repository.BoardRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BoardColumnService {

    private final BoardColumnRepository columnRepo;
    private final EntityManager entityManager;
    private final BoardRepository boardRepo;

    public BoardColumnService(BoardColumnRepository columnRepo, EntityManager entityManager, BoardRepository boardRepo) {
        this.columnRepo = columnRepo;
        this.entityManager = entityManager;
        this.boardRepo = boardRepo;
    }

    public BoardColumn getColumn(UUID id) {
        return columnRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Column with id " + id + " not found"));
    }

    public List<BoardColumn> listColumns() {
        return columnRepo.findAll();
    }

    public BoardColumn createColumn(String name, UUID boardId) {
        BoardColumn column = new BoardColumn();
        column.setName(name);
        column.setPosition(columnRepo.findMaxPositionInBoard(boardId) + 1);
        column.setBoard(entityManager.getReference(Board.class, boardId));
        column = this.columnRepo.save(column);
        return column;
    }





}
