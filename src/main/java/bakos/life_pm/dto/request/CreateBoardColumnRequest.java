package bakos.life_pm.dto.request;

import bakos.life_pm.entity.Board;
import bakos.life_pm.validators.ValidEditor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateBoardColumnRequest {
    String name;
    @ValidEditor(entity = Board.class)
    UUID boardId;
}
