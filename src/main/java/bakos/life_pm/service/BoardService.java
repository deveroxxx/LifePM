package bakos.life_pm.service;


import bakos.life_pm.entity.Board;
import bakos.life_pm.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static bakos.life_pm.constant.Constants.SPARSE_POSITION_GAP;

@Service
public class BoardService {

    private final BoardRepository boardRepo;

    public BoardService(BoardRepository boardRepo) {
        this.boardRepo = boardRepo;

    }

    public Board createBoard(String name) {
        Board board = new Board(name);
        board.setPosition(boardRepo.findMaxBoardPosition() + SPARSE_POSITION_GAP);
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
