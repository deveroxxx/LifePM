package bakos.life_pm.dto.response;

import bakos.life_pm.enums.BoardPermissionEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardNavBarDto {
    UUID id;
    String name;
    BoardPermissionEnum permission;
}
