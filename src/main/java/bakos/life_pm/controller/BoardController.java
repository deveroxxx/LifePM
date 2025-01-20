package bakos.life_pm.controller;

import bakos.life_pm.dto.BoardDto;
import bakos.life_pm.mapper.BoardMapper;
import bakos.life_pm.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/api/board/{boardId}")
    public BoardDto getBoard(@PathVariable(name = "boardId", required = false) UUID id) {
        return BoardMapper.INSTANCE.toDto(boardService.getBoard(id));
    }

    @GetMapping("/api/board/all")
    public List<BoardDto> getAllBoard() {
        return boardService.listBoards().stream().map(BoardMapper.INSTANCE::toDto).toList();
    }

    @PutMapping("/api/board/")
    public BoardDto createBoard(@RequestBody String name) {
        return BoardMapper.INSTANCE.toDto(boardService.createBoard(name));
    }





}
