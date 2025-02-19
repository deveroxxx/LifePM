package bakos.life_pm.dto.request;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.validators.ValidEditor;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateColumnOrderRequest {
    @ValidEditor(entity = BoardColumn.class)
    private UUID previousItemId;
    @ValidEditor(entity = BoardColumn.class)
    private UUID nextItemId;
}

