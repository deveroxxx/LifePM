package bakos.life_pm.dto;

import bakos.life_pm.enums.Priority;
import bakos.life_pm.enums.TodoType;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link bakos.life_pm.entity.Todo}
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodoDto implements Serializable {
    UUID id;
    @NotBlank
    String title;
    String description;
    Priority priority;
    TodoType todoType;
    boolean archived;
    List<TodoTagDto> todoTags;
    Integer position;
}