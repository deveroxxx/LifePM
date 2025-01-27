package bakos.life_pm.unit;

import bakos.life_pm.dto.request.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class BasicUnitTest {

    @Test
    public void test() throws JsonProcessingException {
        String json =  new ObjectMapper().writeValueAsString(new AuthRequest("user", "pass"));
        System.out.println(json);
    }


}
