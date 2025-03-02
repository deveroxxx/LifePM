package bakos.life_pm.unit;

import bakos.life_pm.dto.request.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class BasicUnitTest {

    @Test
    public void test() throws JsonProcessingException {
        String json =  new ObjectMapper().writeValueAsString(new AuthRequest("user", "pass"));
        System.out.println(json);
    }


    @Test
    public void test2() throws Exception {
        String token = JwtTestUtil.generateToken("gigatoken", 100);
        Jwt<?, ?> jwt = Jwts.parser()
                .build()
                .parseClaimsJwt(token);
        System.out.println(jwt);
    }



}
