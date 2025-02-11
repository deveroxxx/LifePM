package bakos.life_pm.controller;

import bakos.life_pm.dto.response.BoardDto;
import bakos.life_pm.dto.request.CreateBoardRequest;
import bakos.life_pm.entity.Board;
import bakos.life_pm.mapper.BoardMapper;
import bakos.life_pm.service.BoardService;
import bakos.life_pm.validators.ValidOwner;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/{boardId}")
    public BoardDto getBoard(@PathVariable(name = "boardId", required = false) UUID id) {
        return BoardMapper.INSTANCE.toDto(boardService.getBoard(id));
    }

    @GetMapping("/all")
    public List<BoardDto> getAllBoard() {
        return boardService.listBoards().stream().map(BoardMapper.INSTANCE::toDto).toList();
    }

    @PostMapping("/create")
    public BoardDto createBoard(@RequestBody CreateBoardRequest request) {
        return BoardMapper.INSTANCE.toDto(boardService.createBoard(request.getName()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBoard(@Valid @ValidOwner(entity = Board.class) @PathVariable(name = "id") UUID id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }





}
