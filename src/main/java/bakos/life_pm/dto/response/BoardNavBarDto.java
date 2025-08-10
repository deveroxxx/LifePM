package bakos.life_pm.dto.response;

import bakos.life_pm.enums.BoardPermissionEnum;

import java.util.UUID;

public record BoardNavBarDto(
        UUID id,
        String name,
        BoardPermissionEnum permission) {
}
