package bakos.life_pm.dto.request;

import bakos.life_pm.entity.Board;
import bakos.life_pm.validators.ValidOwner;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateBoardColumnRequest {
    String name;
    @ValidOwner(entity = Board.class)
    UUID boardId;
}
