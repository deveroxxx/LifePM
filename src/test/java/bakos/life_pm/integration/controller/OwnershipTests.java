package bakos.life_pm.integration.controller;

import bakos.life_pm.dto.request.AuthRequest;
import bakos.life_pm.dto.request.CreateBoardColumnRequest;
import bakos.life_pm.dto.request.CreateBoardRequest;
import bakos.life_pm.dto.response.BoardColumnDto;
import bakos.life_pm.dto.response.BoardDto;
import bakos.life_pm.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static bakos.life_pm.auth.JwtAuthFilter.AUTH_COOKIE_NAME;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class OwnershipTests {

    private static final String USER_A = "user_a";
    private static final String USER_B = "user_b";
    private static final String USER_PASSWORD = "pass";
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Autowired
    private UserService userService;

    // TODO: since we starting everything we could just use rest template
    @Autowired
    private MockMvc mvc;


    @BeforeEach
    public void setUp() throws Exception {
        userService.createUser(USER_A, USER_PASSWORD, "test@test.com");
        userService.createUser(USER_B, USER_PASSWORD, "test2@test.com");
    }


    private ResultActions preformLogin(String username, String password) throws Exception {
        String json =  new ObjectMapper().writeValueAsString(new AuthRequest(username, password));
        return mvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .content(json)
                .contentType("application/json"));
    }

    private ResultActions preformLogout(Cookie cookie) throws Exception {
        return mvc.perform(MockMvcRequestBuilders
                .post("/auth/logout")
                .contentType("application/json"));
    }


    @Test
    void addBoardColumns() throws Exception {
        // USER A Creates a board and ads a column
        Cookie cookie_user_a = preformLogin(USER_A, USER_PASSWORD)
                .andExpect(status().isOk()).andReturn().getResponse().getCookie(AUTH_COOKIE_NAME);
        String request = new ObjectMapper().writeValueAsString(new CreateBoardRequest("Board"));

        BoardDto boardDto = mapper.readValue(mvc.perform(MockMvcRequestBuilders
                .post("/api/boards")
                .content(request)
                .cookie(cookie_user_a)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), BoardDto.class);

        request = new ObjectMapper().writeValueAsString(new CreateBoardColumnRequest("BoardCol", boardDto.getId()));
        BoardColumnDto columnDto = mapper.readValue(mvc.perform(MockMvcRequestBuilders
                        .post("/api/board-columns")
                        .content(request)
                        .cookie(cookie_user_a)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), BoardColumnDto.class);

        // USER B tries to add a column to that board
        Cookie cookie_user_b = preformLogin(USER_B, USER_PASSWORD)
                .andExpect(status().isOk()).andReturn().getResponse().getCookie(AUTH_COOKIE_NAME);

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/board-columns")
                        .content(request)
                        .cookie(cookie_user_b)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }


}
