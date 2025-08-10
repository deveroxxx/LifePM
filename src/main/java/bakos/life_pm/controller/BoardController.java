package bakos.life_pm.controller;

import bakos.life_pm.dto.request.BoardPermissionRequest;
import bakos.life_pm.dto.request.CreateBoardRequest;
import bakos.life_pm.dto.request.UpdateArchivedRequest;
import bakos.life_pm.dto.response.BoardDto;
import bakos.life_pm.dto.response.BoardNavBarDto;
import bakos.life_pm.dto.response.BoardUserResponse;
import bakos.life_pm.entity.Board;
import bakos.life_pm.mapper.BoardMapper;
import bakos.life_pm.mapper.static_mappers.BoardPermissionMapper;
import bakos.life_pm.service.BoardPermissionService;
import bakos.life_pm.service.BoardService;
import bakos.life_pm.validators.ValidEditor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static bakos.life_pm.mapper.static_mappers.BoardPermissionMapper.toBoardUser;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;
    private final BoardPermissionService boardPermissionService;

    public BoardController(BoardService boardService, BoardPermissionService boardPermissionService) {
        this.boardService = boardService;
        this.boardPermissionService = boardPermissionService;
    }

    @PostMapping()
    public BoardDto createBoard(@RequestBody CreateBoardRequest request) {
        return BoardMapper.INSTANCE.toDto(boardService.createBoard(request.name()));
    }

    @GetMapping()
    public List<BoardNavBarDto> getBoardNavBar() {
        return boardService.getBoardNavBarList();
    }

    @PatchMapping("/{boardId}")
    public BoardDto updateBoard(@PathVariable(name = "boardId") UUID id) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{boardId}")
    public BoardDto getBoard(@Valid @ValidEditor(entity = Board.class, requireEditor = false) @PathVariable(name = "boardId") UUID id) {
        return BoardMapper.INSTANCE.toDto(boardService.getBoard(id));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@Valid @ValidEditor(entity = Board.class) @PathVariable(name = "boardId") UUID id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{boardId}/archive")
    public ResponseEntity<Void> setArchived(@Valid @ValidEditor(entity = Board.class) @PathVariable(name = "boardId") UUID id,
                                            @RequestBody UpdateArchivedRequest request) {
        boardService.setArchived(id, request.archived());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}/users")
    public List<BoardUserResponse> getBoardUsers(@Valid @ValidEditor(entity = Board.class, requireEditor = false) @PathVariable(name = "boardId") UUID boardId) {
        return boardPermissionService.findByBoard(boardId).stream().map(BoardPermissionMapper::toBoardUser).toList();
    }

    @PostMapping("/{boardId}/users/{userName}")
    public BoardUserResponse addOrUpdate(@Valid @ValidEditor(entity = Board.class) @PathVariable(name = "boardId") UUID boardId,
                                @PathVariable(name = "userName") String userName,
                                @RequestBody BoardPermissionRequest request) {
        return toBoardUser(boardPermissionService.addOrUpdatePermission(boardId, userName, request.permission()));
    }

    @DeleteMapping("/{boardId}/users/{userName}")
    public ResponseEntity<Void> deleteBoardUser(@Valid @ValidEditor(entity = Board.class) @PathVariable(name = "boardId") UUID boardId,
                                                @PathVariable(name = "userName") String userName) {
        boardPermissionService.removeUserFromBoard(boardId, userName);
        return ResponseEntity.ok().build();
    }













}
