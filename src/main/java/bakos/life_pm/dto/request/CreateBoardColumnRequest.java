package bakos.life_pm.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateBoardColumnRequest {
    String name;
    UUID boardId;


}
