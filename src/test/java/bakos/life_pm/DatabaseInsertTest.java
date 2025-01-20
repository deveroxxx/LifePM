package bakos.life_pm;


import bakos.life_pm.entity.Board;
import bakos.life_pm.service.BoardService;
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
    private BoardService boardService;

    @Test
    public void test() {
        Board board = boardService.createBoard("TestBoard");
        log.info(String.valueOf(board.getId()));
        boardService.getBoard(board.getId());
        log.info("Success :)");
    }


}
