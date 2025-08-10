package bakos.life_pm.dto.response;

import bakos.life_pm.enums.BoardPermissionEnum;


public record BoardUserResponse(
        String userName,
        BoardPermissionEnum permission) {
}
