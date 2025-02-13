package bakos.life_pm.controller;

import bakos.life_pm.dto.request.CreateTodoRequest;
import bakos.life_pm.dto.request.UpdateTodoOrderRequest;
import bakos.life_pm.dto.response.TodoDto;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.mapper.TodoMapper;
import bakos.life_pm.service.TodoService;
import bakos.life_pm.validators.ValidOwner;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
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
    public TodoDto updateTodo(@PathVariable(name = "todoId") UUID id) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@Valid @ValidOwner(entity = Todo.class) @PathVariable(name = "id") UUID id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{todoId}/reorder")
    public ResponseEntity<Void> updatePosition(@Valid @RequestBody UpdateTodoOrderRequest request) {
        todoService.updateTodoPosition(
                request.getMovedItemId(),
                request.getPreviousItemId(),
                request.getNextItemId(),
                request.getNewColumnId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{todoId}/image")
    public TodoDto uploadImage(@Valid @ValidOwner(entity = Todo.class) @PathVariable(name = "todoId") UUID id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{todoId}/file")
    public TodoDto uploadFile(@Valid @ValidOwner(entity = Todo.class) @PathVariable(name = "todoId") UUID id) {
        throw new UnsupportedOperationException();
    }








}
