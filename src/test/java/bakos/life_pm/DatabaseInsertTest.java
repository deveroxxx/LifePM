package bakos.life_pm;


import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.repository.TodoRepository;
import bakos.life_pm.service.BoardColumnService;
import bakos.life_pm.service.BoardService;
import bakos.life_pm.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootTest
//@WithMockUser(username = "a@a.com")
public class DatabaseInsertTest {


    @Autowired
    private BoardService boardService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TodoService todoService;

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private TestUtils testUtils;

    @Test
    public void test() {
        Board board = boardService.createBoard("TestBoard");
        log.info(String.valueOf(board.getId()));
        boardService.getBoard(board.getId());
        log.info("Success :)");
    }

    @Test
    void testCreateBoardAddColumnAndAddTodo() {
        for (int i = 0; i < 1; i++) {
            Board board = boardService.createBoard("New Board " + i);
            for (int j = 0; j < 4; j++) {
                BoardColumn column = boardColumnService.createColumn("New Column "+ j, board.getId());
                for (int k = 0; k <4; k++) {
                    Todo todo = todoService.createTodo("New Todo " + j + k, column.getId());
                }
            }
        }
    }


}
