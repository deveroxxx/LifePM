package bakos.life_pm.cucumber;

import bakos.life_pm.dto.request.AuthRequest;
import bakos.life_pm.dto.request.RegisterUserRequest;
import bakos.life_pm.entity.Customer;
import bakos.life_pm.repository.UserRepository;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class UserHandlingCheckSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<Object> response;

    @Autowired
    private UserRepository userRepository;

    @When("No user exists with username {string}")
    public void user_not_exists_with_username(String userName) {
        assertFalse(userRepository.existsByUserName(userName));
    }

    @When("User register with user name: {string}, password: {string} and email: {string}")
    public void user_register_with_user_name_and_password(String userName, String password, String email) {
        RegisterUserRequest request = new RegisterUserRequest(userName, password, email);
        response = restTemplate.postForEntity("/auth/signup", request, Object.class);
        assertSame(HttpStatus.OK, response.getStatusCode(), Objects.requireNonNull(response.getBody()).toString());
    }

    @When("User logs in with user name: {string}, password: {string}")
    public void user_logs_in_with_user_name_and_password(String userName, String password) {
        restTemplate.postForEntity("/auth/login", new AuthRequest(userName, password), Customer.class);
        assertSame(HttpStatus.OK, response.getStatusCode());
    }



}
