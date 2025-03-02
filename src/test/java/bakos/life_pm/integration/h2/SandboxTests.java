package bakos.life_pm.integration.h2;


import bakos.life_pm.TestUtils;
import bakos.life_pm.dto.response.TodoDetailsDto;
import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.mapper.complex.TodoDetailsMapper;
import bakos.life_pm.repository.TodoRepository;
import bakos.life_pm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@Disabled
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

    @Autowired
    private TodoDetailsMapper todoDetailsMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FileAttachmentService fileAttachmentService;


    @Test
    public void testCreateUser() {
        Board board = boardService.createBoard("New Board ");
        BoardColumn column = boardColumnService.createColumn("New Column ", board.getId());
        Todo todo = todoService.createTodo("New Todo ", column.getId());
        todo = todoRepository.findById(todo.getId()).get();
    }


    @Test
    public void testMapper() {
        Board board = boardService.createBoard("New Board ");
        BoardColumn column = boardColumnService.createColumn("New Column ", board.getId());
        Todo todo = todoService.createTodo("New Todo ", column.getId());

        commentService.addComment(todo.getId(), "This is a comment");

        TodoDetailsDto dto = todoDetailsMapper.toDto(todo);
        System.out.println(dto);
    }

    @Test
    public void test_file_upload() {
        Board board = boardService.createBoard("New Board ");
        BoardColumn column = boardColumnService.createColumn("New Column ", board.getId());
        Todo todo = todoService.createTodo("New Todo ", column.getId());

        MultipartFile file = new MockMultipartFile(
                "file",                      // Field name
                "test.txt",                  // Original file name
                MediaType.TEXT_PLAIN_VALUE,  // Content type
                "Hello, world!".getBytes(StandardCharsets.UTF_8) // File content
        );

        fileAttachmentService.saveFile(file, todo.getId());

    }



}
