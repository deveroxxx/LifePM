package bakos.life_pm.integration.h2;

import bakos.life_pm.TestUtils;
import bakos.life_pm.dto.response.BoardDto;
import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.mapper.BoardMapper;
import bakos.life_pm.repository.TodoRepository;
import bakos.life_pm.service.BoardColumnService;
import bakos.life_pm.service.BoardService;
import bakos.life_pm.service.TodoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Transactional
public class TodoTests extends TestUtils implements H2BaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BoardService boardService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private TestUtils testUtils;

    @Test
    void testDatabaseConnection() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void boardPositionInsertTest() {
        boardService.createBoard("Board 1");
        boardService.createBoard("Board 2");
        Board board_3 = boardService.createBoard("Board 3");
        assertEquals(3 ,board_3.getPosition(), "Board position should be 2");
    }

    @Test
    void testCreateBoardAddColumnAndAddTodo() {
        Board board = boardService.createBoard("Board 1");
        BoardColumn column = boardColumnService.createColumn("Column 1", board.getId());
        Todo todo = todoService.createTodo("Todo 1", column.getId());
        simulateNewTransaction();
        board = boardService.getBoard(board.getId());

        BoardDto dto = BoardMapper.INSTANCE.toDto(board);
        log.info(":)");
    }
}
