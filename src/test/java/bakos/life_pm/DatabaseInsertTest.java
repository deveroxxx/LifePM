package bakos.life_pm;


import bakos.life_pm.service.CoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseInsertTest {

    @Autowired
    private CoreService coreService;

    @Test
    public void test() {
        System.out.println("test");
        coreService.createBoard("TestBoard", "Test Board Description");
    }


}
