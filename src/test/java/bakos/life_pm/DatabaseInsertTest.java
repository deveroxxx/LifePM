package bakos.life_pm;


import bakos.life_pm.entity.Board;
import bakos.life_pm.service.CoreService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
@Transactional
public class DatabaseInsertTest {


    @Autowired
    private CoreService coreService;

    @Test
    public void test() {
        Board board = coreService.createBoard("TestBoard", "Test Board Description");
        log.info(String.valueOf(board.getId()));
        coreService.getBoard(board.getId());
        log.info("Success :)");
    }


}
