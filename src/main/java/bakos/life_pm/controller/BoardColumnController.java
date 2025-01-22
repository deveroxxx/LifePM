package bakos.life_pm.controller;

import bakos.life_pm.dto.BoardColumnDto;
import bakos.life_pm.dto.request.UpdateOrderRequest;
import bakos.life_pm.mapper.BoardColumnMapper;
import bakos.life_pm.service.BoardColumnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
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

    @PostMapping("/api/board-column/create")
    public BoardColumnDto createColumn(@RequestBody String name, @RequestBody UUID boardId) {
        return BoardColumnMapper.INSTANCE.toDto(boardColumnService.createColumn(name, boardId));
    }

    @PutMapping("/api/board-column/update-position")
    public ResponseEntity<Void> updatePosition(@RequestBody UpdateOrderRequest request) {
        boardColumnService.updateColumnPosition(
                request.getMovedItemId(),
                request.getPreviousItemId(),
                request.getNextItemId());
        return ResponseEntity.ok().build();
    }




}
