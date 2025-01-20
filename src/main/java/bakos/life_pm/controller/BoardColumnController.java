package bakos.life_pm.controller;

import bakos.life_pm.dto.BoardColumnDto;
import bakos.life_pm.mapper.BoardColumnMapper;
import bakos.life_pm.service.BoardColumnService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public class BoardColumnController {

    private final BoardColumnService boardColumnService;

    public BoardColumnController(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }


    @GetMapping("/api/board-column/{columnId}")
    public BoardColumnDto getColumn(@PathVariable(name = "columnId") UUID id) {
        return BoardColumnMapper.INSTANCE.toDto(boardColumnService.getColumn(id));
    }

    @GetMapping("/api/board-column/all")
    public List<BoardColumnDto> getAllColumn() {
        return boardColumnService.listColumns().stream().map(BoardColumnMapper.INSTANCE::toDto).toList();
    }

    @PutMapping("/api/board-column/")
    public BoardColumnDto createColumn(@RequestBody String name, @RequestBody UUID boardId) {
        return BoardColumnMapper.INSTANCE.toDto(boardColumnService.createColumn(name, boardId));
    }




}
