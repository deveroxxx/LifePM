package bakos.life_pm.dto.request;

import bakos.life_pm.entity.Board;
import bakos.life_pm.validators.ValidEditor;

import java.util.UUID;

public record CreateBoardColumnRequest(
        String name,
        @ValidEditor(entity = Board.class) UUID boardId) {
}
