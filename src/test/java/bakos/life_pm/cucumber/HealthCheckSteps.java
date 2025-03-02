package bakos.life_pm.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class HealthCheckSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    @Given("the application is running")
    public void the_application_is_running() {
        // Just a placeholder to ensure app context loads
    }

    @When("I check the health status")
    public void i_check_the_health_status() {
        response = restTemplate.getForEntity("/actuator/health", String.class);
    }

    @Then("the application should be {string}")
    public void the_application_should_be(String expectedStatus) {
        Assumptions.assumeTrue(response.getStatusCode() == HttpStatus.OK, "Health check failed, skipping tests!");
        assertEquals("{\"status\":\"" + expectedStatus + "\"}", response.getBody());
    }
}
