package bakos.life_pm.service;


import bakos.life_pm.dto.response.BoardNavBarDto;
import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardPermission;
import bakos.life_pm.enums.BoardPermissionEnum;
import bakos.life_pm.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static bakos.life_pm.constant.Constants.SPARSE_POSITION_GAP;

@Service
public class BoardService {

    private final BoardRepository boardRepo;
    @Autowired
    private BoardPermissionService boardPermissionService;

    public BoardService(BoardRepository boardRepo) {
        this.boardRepo = boardRepo;
    }

    @Transactional
    public Board createBoard(String name) {
        Board board = new Board(name);
        board.setUserName(Utils.getUserFromSecurityContext());
        board.setPosition(boardRepo.findMaxBoardPosition() + SPARSE_POSITION_GAP);

        BoardPermission boardPermission = new BoardPermission();
        boardPermission.setBoard(board);
        boardPermission.setUserName(Utils.getUserFromSecurityContext());
        boardPermission.setPermission(BoardPermissionEnum.OWNER);
        board.getPermissions().add(boardPermission);

        board = this.boardRepo.save(board);
        return board;
    }


    @Transactional
    public void deleteBoard(UUID id) {
        getBoard(id);
        boardRepo.deleteById(id);
    }

    public Board getBoard(UUID id) {
        return boardRepo.findByIdOrThrow(id);
    }

    @Transactional
    public List<BoardNavBarDto> getBoardNavBarList() {
        List<BoardPermission> permissions = boardPermissionService.findByUserName(Utils.getUserFromSecurityContext());
        List<BoardNavBarDto> result = new ArrayList<>();
        for (BoardPermission permission : permissions) {
            BoardNavBarDto boardNavBarDto = new BoardNavBarDto();
            boardNavBarDto.setId(permission.getBoard().getId());
            boardNavBarDto.setName(permission.getBoard().getName());
            boardNavBarDto.setPermission(permission.getPermission());
            result.add(boardNavBarDto);
        }
        return result;
    }

}
