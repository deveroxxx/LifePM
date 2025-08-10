package bakos.life_pm.dto.request;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.validators.ValidEditor;

import java.util.UUID;

public record UpdateColumnOrderRequest(
        @ValidEditor(entity = BoardColumn.class) UUID previousItemId,
        @ValidEditor(entity = BoardColumn.class) UUID nextItemId) {
}

