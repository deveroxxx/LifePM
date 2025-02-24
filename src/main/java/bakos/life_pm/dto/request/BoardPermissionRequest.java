package bakos.life_pm.dto.request;

import bakos.life_pm.enums.BoardPermissionEnum;
import lombok.Data;

@Data
public class BoardPermissionRequest {
    private BoardPermissionEnum permission;
}
