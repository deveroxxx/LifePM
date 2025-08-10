package bakos.life_pm.dto.response;

import bakos.life_pm.enums.Priority;
import bakos.life_pm.enums.TodoType;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link bakos.life_pm.entity.Todo}
 */
public record TodoDto(
        UUID id,
        @NotBlank String title,
        String description,
        Priority priority,
        TodoType todoType,
        boolean archived,
        List<TodoTagDto> todoTags,
        Integer position) {
}