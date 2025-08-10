package bakos.life_pm.dto.request;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.validators.ValidEditor;

import java.util.UUID;

public record CreateTodoRequest(
        String name,
        @ValidEditor(entity = BoardColumn.class) UUID columnId) {
}
