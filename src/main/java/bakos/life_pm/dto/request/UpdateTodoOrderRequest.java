package bakos.life_pm.dto.request;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.validators.ValidOwner;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateTodoOrderRequest {
    @ValidOwner(entity = Todo.class)
    private UUID movedItemId;
    @ValidOwner(entity = Todo.class)
    private UUID previousItemId;
    @ValidOwner(entity = Todo.class)
    private UUID nextItemId;
    @ValidOwner(entity = BoardColumn.class)
    private UUID newColumnId;
}

