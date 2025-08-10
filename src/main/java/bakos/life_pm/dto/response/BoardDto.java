package bakos.life_pm.dto.response;

import java.util.List;
import java.util.UUID;

public record BoardDto(
        UUID id,
        String name,
        String description,
        Integer position,
        List<BoardColumnDto> columns) {
}
