package bakos.life_pm.integration.controller;


import bakos.life_pm.dto.request.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserAuthTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldAllowAccessToLogin() throws Exception {
        String json =  new ObjectMapper().writeValueAsString(new AuthRequest("user", "pass"));
        mvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .content(json)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAllowAccessToSignup() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/auth/signup")).andExpect(status().isOk());
    }

    @Test
    public void shouldNotAllowAccessToUnauthenticatedUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/test")).andExpect(status().isForbidden());
    }

}
