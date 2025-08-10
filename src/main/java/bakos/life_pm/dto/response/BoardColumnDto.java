package bakos.life_pm.dto.response;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public record BoardColumnDto(
        UUID id,
        LocalDateTime createdOn,
        LocalDateTime updatedOn,
        @NotBlank String name,
        Integer position,
        List<TodoDto> todos) {
}