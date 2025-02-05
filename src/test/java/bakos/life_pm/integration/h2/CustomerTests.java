package bakos.life_pm.integration.h2;

import bakos.life_pm.entity.Customer;
import bakos.life_pm.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Transactional
public class CustomerTests implements H2BaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        Customer user = userService.createUser("user", "password", "email@email.com");
    }




}
