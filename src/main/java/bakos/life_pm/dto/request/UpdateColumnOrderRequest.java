package bakos.life_pm.dto.request;

import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.validators.ValidOwner;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateColumnOrderRequest {
    @ValidOwner(entity = BoardColumn.class)
    private UUID previousItemId;
    @ValidOwner(entity = BoardColumn.class)
    private UUID nextItemId;
}

