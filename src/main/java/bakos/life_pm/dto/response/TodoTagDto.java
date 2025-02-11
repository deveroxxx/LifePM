package bakos.life_pm.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
public class TodoTagDto implements Serializable {
    private UUID id;
    private String name;
}