package bakos.life_pm.dto.request;

import lombok.Data;

@Data
public class PatchTodoRequest {
    String title;
    String description;
}
