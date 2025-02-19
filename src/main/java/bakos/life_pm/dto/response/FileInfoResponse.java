package bakos.life_pm.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileInfoResponse {
    UUID id;
    String name;
    String type;
    String url;
}
