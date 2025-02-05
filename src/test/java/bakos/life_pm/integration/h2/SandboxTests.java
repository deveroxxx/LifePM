package bakos.life_pm.integration.h2;


import bakos.life_pm.TestUtils;
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
import org.springframework.security.test.context.support.WithMockUser;

@Slf4j
@WithMockUser
public class SandboxTests extends TestUtils implements H2BaseTest {

    @Autowired
    private BoardService boardService;


    @Autowired
    private TodoService todoService;

    @Autowired
    private BoardColumnService boardColumnService;

    @Autowired
    private TodoRepository todoRepository;


    @Test
    public void testCreateUser() {
        Board board = boardService.createBoard("New Board ");
        BoardColumn column = boardColumnService.createColumn("New Column ", board.getId());
        Todo todo = todoService.createTodo("New Todo ", column.getId());
        todo = todoRepository.findById(todo.getId()).get();
        log.info("User id: " + todo.getUserName());
    }


}
