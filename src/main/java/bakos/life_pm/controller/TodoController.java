package bakos.life_pm.controller;

import bakos.life_pm.dto.request.CreateCommentRequest;
import bakos.life_pm.dto.request.CreateTodoRequest;
import bakos.life_pm.dto.request.PatchTodoRequest;
import bakos.life_pm.dto.request.UpdateTodoOrderRequest;
import bakos.life_pm.dto.response.CommentDto;
import bakos.life_pm.dto.response.FileInfoResponse;
import bakos.life_pm.dto.response.TodoDto;
import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.mapper.CommentMapper;
import bakos.life_pm.mapper.TodoMapper;
import bakos.life_pm.mapper.static_mappers.FileAttachmentMapper;
import bakos.life_pm.service.CommentService;
import bakos.life_pm.service.FileAttachmentService;
import bakos.life_pm.service.TodoService;
import bakos.life_pm.validators.ValidEditor;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    private final FileAttachmentService fileAttachmentService;
    private final CommentService commentService;

    public TodoController(TodoService todoService, FileAttachmentService fileAttachmentService, CommentService commentService) {
        this.todoService = todoService;
        this.fileAttachmentService = fileAttachmentService;
        this.commentService = commentService;
    }

    @PostMapping()
    public TodoDto createTodo(@Valid @RequestBody CreateTodoRequest request) {
        return TodoMapper.INSTANCE.toDto(todoService.createTodo(request.getName(), request.getColumnId()));
    }

    @GetMapping()
    public TodoDto getTodos() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{todoId}")
    public TodoDto getTodo(@PathVariable(name = "todoId") UUID id) {
        return TodoMapper.INSTANCE.toDto(todoService.getTodo(id));
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<Void> updateTodo(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID id,
                              @RequestBody PatchTodoRequest request) {
        todoService.patchTodo(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "id") UUID id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{todoId}/reorder")
    public ResponseEntity<Void> updatePosition(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID id,
                                               @Valid @RequestBody UpdateTodoOrderRequest request) {
        todoService.updateTodoPosition(
                id,
                request.getPreviousItemId(),
                request.getNextItemId(),
                request.getNewColumnId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{todoId}/images")
    public FileInfoResponse uploadImage(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID todoId,
                                        @RequestParam("file") MultipartFile file) {
        FileAttachment attachment = fileAttachmentService.saveFile(file, todoId);
        return FileAttachmentMapper.toTodoImageInfo(attachment);
    }

    @GetMapping("/{todoId}/images")
    public List<FileInfoResponse> getImages(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID todoId) {
        return fileAttachmentService.getFileAttachmentByParentId(todoId)
                .stream().map(FileAttachmentMapper::toTodoImageInfo).toList();
    }

    @GetMapping("/{todoId}/images/{imageId}/{filename}")
    public ResponseEntity<Resource> getImage(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID todoId,
                                             @PathVariable(name = "imageId") UUID imageId,
                                             @PathVariable(name = "filename") String filename) {
        //TODO: validate todoId and imageId relation and maybe the filename
        InputStream inputStream = fileAttachmentService.downloadFile(imageId);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/{todoId}/images/{imageId}")
    public TodoDto deleteImage(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID todoId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{todoId}/files")
    public TodoDto uploadFile(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID todoId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{todoId}/comments")
    public CommentDto createComment(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID todoId,
                                    @RequestBody CreateCommentRequest request) {
        return CommentMapper.INSTANCE.toDto(commentService.addComment(todoId, request.getText()));
    }

    @GetMapping("/{todoId}/comments")
    public List<CommentDto> getComments(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID todoId) {
        return commentService.getComments(todoId).stream().map(CommentMapper.INSTANCE::toDto).toList();
    }

    @PostMapping("/{todoId}/comments/{commentId}")
    public TodoDto editComment(@Valid @ValidEditor(entity = Todo.class) @PathVariable(name = "todoId") UUID todoId) {
        throw new UnsupportedOperationException();
    }









}
