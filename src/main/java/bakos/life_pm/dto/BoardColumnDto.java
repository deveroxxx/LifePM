package bakos.life_pm.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardColumnDto implements Serializable {
    UUID id;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
    @NotBlank
    String name;
    Integer position;
}