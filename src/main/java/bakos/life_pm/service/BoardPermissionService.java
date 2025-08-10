package bakos.life_pm.service;

import bakos.life_pm.entity.*;
import bakos.life_pm.enums.BoardPermissionEnum;
import bakos.life_pm.exception.BusinessLogicRtException;
import bakos.life_pm.repository.BoardPermissionRepository;
import bakos.life_pm.repository.BoardRepository;
import bakos.life_pm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardPermissionService {

    private final BoardPermissionRepository boardPermissionRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardPermissionService(BoardPermissionRepository boardPermissionRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.boardPermissionRepository = boardPermissionRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public BoardPermission addOrUpdatePermission(UUID boardId, String userName, BoardPermissionEnum permission) {
        if (userRepository.findByUserName(userName).isEmpty()) throw new BusinessLogicRtException("User does not exist!");
        Optional<BoardPermission> bp = boardPermissionRepository.findByBoardIdAndUserName(boardId, userName);
        if (bp.isPresent()) {
            bp.get().setPermission(permission);
            return bp.get();
        } else {
            BoardPermission boardPermission = new BoardPermission();
            boardPermission.setPermission(permission);
            boardPermission.setUserName(userName);
            boardPermission.setBoard(boardRepository.findByIdOrThrow(boardId));
            boardPermission = boardPermissionRepository.save(boardPermission);
            return boardPermission;
        }
    }

    public List<BoardPermission> findByBoard(UUID boardId) {
        return boardPermissionRepository.findByBoard(boardRepository.findByIdOrThrow(boardId));
    }

    @Transactional
    public void removeUserFromBoard(UUID boardId, String userName) {
        Optional<BoardPermission> permission = boardPermissionRepository.findByBoardIdAndUserName(boardId, userName);
        permission.ifPresent(boardPermissionRepository::delete);
    }

    // Tried to do this with sealed classes, but then I would have to make the entities final, which is not good for proxy and lazy stuff.
    // Switch case also not working because of the ? wildcards.
    // A different version would be @PreAuthorize("@perm.canEditColumn(#columnId)") but with currently how I handle the user auth
    // on different fields I think it's cleaner this way. The above would be good for "ADMIN" and other "easy" checks.
    @Transactional
    public Optional<BoardPermissionEnum> getBoardPermission(UUID id, Class<? extends CustomerRelated> entityClass, String userName){
        if (entityClass == Todo.class) {
            return boardPermissionRepository.findByTodoId(id, userName);
        }
        if (entityClass == BoardColumn.class) {
            return boardPermissionRepository.findByColumnId(id, userName);
        }
        if (entityClass == Board.class) {
            return boardPermissionRepository.findByBoardId(id, userName);
        }
        throw new AssertionError("Unsupported entity class: " + entityClass);
    }

    public List<BoardPermission> findByUserName(String userName) {
        return boardPermissionRepository.findByUserName(userName);
    }

}
