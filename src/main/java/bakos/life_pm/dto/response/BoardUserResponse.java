package bakos.life_pm.dto.response;

import bakos.life_pm.enums.BoardPermissionEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardUserResponse {
    String userName;
    BoardPermissionEnum permission;
}
