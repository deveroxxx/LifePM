package bakos.life_pm.integration.controller;


import bakos.life_pm.dto.request.AuthRequest;
import bakos.life_pm.dto.response.LoginResponse;
import bakos.life_pm.repository.UserRepository;
import bakos.life_pm.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CustomerAuthTests {

    public static final String USERNAME = "user";
    public static final String PASSWORD = "pass";
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    protected EntityManager entityManager;

    private void simulateNewTransaction() {
        entityManager.flush();
        entityManager.clear();
    }

    // TODO: since we starting everything we could just use rest template
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService.createUser(USERNAME, PASSWORD, "test@test.com");
    }

    private ResultActions preformLogin(String username, String password) throws Exception {
        String json =  new ObjectMapper().writeValueAsString(new AuthRequest(username, password));
        return mvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .content(json)
                .contentType("application/json"));
    }

    @Test
    public void correctLoginShouldBeOk() throws Exception {
        System.out.println("User count" + userRepository.findAll().size());
        preformLogin(USERNAME, PASSWORD).andExpect(status().isOk());
    }

    @Test
    public void loginWithWrongPasswordShouldBeUnauthorized() throws Exception {
        preformLogin(USERNAME, "wrong_pass").andExpect(status().isUnauthorized());
    }

    @Test
    public void loginWithWrongUserShouldBeUnauthorized() throws Exception {
        preformLogin("wrongUser", PASSWORD).andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/boards")).andExpect(status().isForbidden());
    }

    @Test
    public void shouldAllowAccessToAuthenticatedUsers() throws Exception {
        String contentAsString = preformLogin(USERNAME, PASSWORD).andReturn().getResponse().getContentAsString();
        LoginResponse loginResponse = mapper.readValue(contentAsString, LoginResponse.class);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/boards")
                .header("Authorization", "Bearer " + loginResponse.getToken()))
        .andExpect(status().isOk());
    }
}
