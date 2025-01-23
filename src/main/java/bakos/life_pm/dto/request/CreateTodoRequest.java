package bakos.life_pm.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateTodoRequest {
    private String name;
    private UUID columnId;
}
