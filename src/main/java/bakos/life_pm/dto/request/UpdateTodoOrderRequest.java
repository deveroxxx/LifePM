package bakos.life_pm.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateTodoOrderRequest extends UpdateOrderRequest {
    private UUID newColumnId;
}

