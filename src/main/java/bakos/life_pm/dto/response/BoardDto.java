package bakos.life_pm.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardDto implements Serializable {
    UUID id;
    String name;
    String description;
    Integer position;
    List<BoardColumnDto> columns;
}
