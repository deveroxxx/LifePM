package bakos.life_pm.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateOrderRequest {
    private UUID movedItemId;
    private UUID previousItemId;
    private UUID nextItemId;
}

