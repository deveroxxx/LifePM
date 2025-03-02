package bakos.life_pm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserRequest {

    private String username;
    private String password;
    private String email;

}
