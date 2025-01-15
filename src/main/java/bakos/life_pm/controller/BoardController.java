package bakos.life_pm.controller;

import bakos.life_pm.dto.BoardDto;
import bakos.life_pm.mapper.BoardMapper;
import bakos.life_pm.service.CoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BoardController {

    private final CoreService coreService;

    public BoardController(CoreService coreService) {
        this.coreService = coreService;
    }


    @GetMapping("/api/board/{boardId}")
    public BoardDto getBoard(@PathVariable(name = "boardId", required = false) UUID id) {
        return BoardMapper.INSTANCE.toDto(coreService.getBoard(id));
    }

    @GetMapping("/api/board/all")
    public List<BoardDto> getAllBoard() {
        return coreService.listBoards().stream().map(BoardMapper.INSTANCE::toDto).toList();
    }

    @PutMapping("/api/board/")
    public BoardDto createBoard(@RequestBody String name) {
        return BoardMapper.INSTANCE.toDto(coreService.createBoard(name, null));
    }





}
