package bakos.life_pm.dto.response;

import java.util.UUID;

public record FileInfoResponse(
        UUID id,
        String name,
        String type,
        String url) {
}
