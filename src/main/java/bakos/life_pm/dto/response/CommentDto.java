package bakos.life_pm.dto.response;

import java.util.UUID;

public record CommentDto(
        UUID id,
        boolean owner,
        UUID parentId,
        String userName,
        String content) {
}
