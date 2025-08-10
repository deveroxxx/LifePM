package bakos.life_pm.dto.response;

import java.util.List;


public record TodoDetailsDto(
        @com.fasterxml.jackson.annotation.JsonUnwrapped TodoDto todo, // TODO: check this if this still works as before record
        List<CommentDto> comments) {
}
