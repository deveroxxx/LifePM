package bakos.life_pm.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto implements Serializable {

    private UUID id;
    private UUID parentId;
    private String userName;
    private String content;
}
