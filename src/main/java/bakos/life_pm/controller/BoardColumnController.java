package bakos.life_pm.controller;

import bakos.life_pm.dto.request.CreateBoardColumnRequest;
import bakos.life_pm.dto.request.UpdateColumnOrderRequest;
import bakos.life_pm.dto.response.BoardColumnDto;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.mapper.BoardColumnMapper;
import bakos.life_pm.service.BoardColumnService;
import bakos.life_pm.validators.ValidEditor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/board-columns")
public class BoardColumnController {

    private final BoardColumnService boardColumnService;

    public BoardColumnController(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }

    @PostMapping()
    public BoardColumnDto createColumn(@Valid @RequestBody CreateBoardColumnRequest request) {
        return BoardColumnMapper.INSTANCE.toDto(boardColumnService.createColumn(request.getName(), request.getBoardId()));
    }

    @GetMapping()
    public List<BoardColumnDto> getAllColumn() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{columnId}")
    public BoardColumnDto getColumn(@Valid @ValidEditor(entity = BoardColumn.class, requireEditor = false) @PathVariable(name = "columnId") UUID id) {
        return BoardColumnMapper.INSTANCE.toDto(boardColumnService.getColumn(id));
    }

    @PatchMapping("/{columnId}")
    public BoardColumnDto updateColumn(@Valid @ValidEditor(entity = BoardColumn.class) @PathVariable(name = "columnId") UUID id) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{columnId}")
    public ResponseEntity<Void> deleteColumn(@Valid @ValidEditor(entity = BoardColumn.class) @PathVariable(name = "columnId") UUID id) {
        boardColumnService.deleteColumn(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{columnId}/reorder")
    public ResponseEntity<Void> updatePosition(@Valid @ValidEditor(entity = BoardColumn.class) @PathVariable(name = "columnId") UUID id,
                                               @Valid @RequestBody UpdateColumnOrderRequest request) {
        boardColumnService.updateColumnPosition(id, request.getPreviousItemId(), request.getNextItemId());
        return ResponseEntity.ok().build();
    }
}
