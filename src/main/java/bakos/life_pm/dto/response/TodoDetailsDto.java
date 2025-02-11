package bakos.life_pm.dto.response;

import bakos.life_pm.enums.Priority;
import bakos.life_pm.enums.TodoType;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodoDetailsDto {
    UUID id;
    @NotBlank
    String title;
    String description;
    Priority priority;
    TodoType todoType;
    boolean archived;
    List<TodoTagDto> todoTags;
    List<CommentDto> comments;
    Integer position;

}
