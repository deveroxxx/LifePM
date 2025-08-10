package bakos.life_pm.dto.response;

import java.util.UUID;


public record TodoTagDto(
        UUID id,
        String name) {
}