package bakos.life_pm.dto.request;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.validators.ValidEditor;

import java.util.UUID;


public record UpdateTodoOrderRequest(
        @ValidEditor(entity = Todo.class) UUID movedItemId,
        @ValidEditor(entity = Todo.class) UUID previousItemId,
        @ValidEditor(entity = Todo.class) UUID nextItemId,
        @ValidEditor(entity = BoardColumn.class) UUID newColumnId) {
}

