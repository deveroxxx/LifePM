package bakos.life_pm.dto.request;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.validators.ValidEditor;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateTodoOrderRequest {
    @ValidEditor(entity = Todo.class)
    private UUID movedItemId;
    @ValidEditor(entity = Todo.class)
    private UUID previousItemId;
    @ValidEditor(entity = Todo.class)
    private UUID nextItemId;
    @ValidEditor(entity = BoardColumn.class)
    private UUID newColumnId;
}

