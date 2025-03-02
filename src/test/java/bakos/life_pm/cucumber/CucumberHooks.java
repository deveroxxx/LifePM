package bakos.life_pm.cucumber;

import io.cucumber.java.Before;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class CucumberHooks {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void verifyApplicationIsHealthy() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Health check failed, skipping tests!");
        Assertions.assertTrue(Objects.requireNonNull(response.getBody()).contains("\"status\":\"UP\""), "Application is not UP. Health response: " + response.getBody());
    }
}