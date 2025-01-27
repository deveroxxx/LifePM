package bakos.life_pm.controller;

import bakos.life_pm.dto.BoardColumnDto;
import bakos.life_pm.dto.request.CreateBoardColumnRequest;
import bakos.life_pm.dto.request.UpdateOrderRequest;
import bakos.life_pm.mapper.BoardColumnMapper;
import bakos.life_pm.service.BoardColumnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/board-column")
public class BoardColumnController {

    private final BoardColumnService boardColumnService;

    public BoardColumnController(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }


    @GetMapping("/{columnId}")
    public BoardColumnDto getColumn(@PathVariable(name = "columnId") UUID id) {
        return BoardColumnMapper.INSTANCE.toDto(boardColumnService.getColumn(id));
    }

    @GetMapping("/all")
    public List<BoardColumnDto> getAllColumn() {
        return boardColumnService.listColumns().stream().map(BoardColumnMapper.INSTANCE::toDto).toList();
    }

    @PostMapping("/create")
    public BoardColumnDto createColumn(@RequestBody CreateBoardColumnRequest request) {
        return BoardColumnMapper.INSTANCE.toDto(boardColumnService.createColumn(request.getName(), request.getBoardId()));
    }

    @PutMapping("/update-position")
    public ResponseEntity<Void> updatePosition(@RequestBody UpdateOrderRequest request) {
        boardColumnService.updateColumnPosition(
                request.getMovedItemId(),
                request.getPreviousItemId(),
                request.getNextItemId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable(name = "id") UUID id) {
        boardColumnService.deleteColumn(id);
        return ResponseEntity.ok().build();
    }





}
