package bakos.life_pm.controller;

import bakos.life_pm.dto.TodoDto;
import bakos.life_pm.dto.request.CreateTodoRequest;
import bakos.life_pm.dto.request.UpdateTodoOrderRequest;
import bakos.life_pm.mapper.TodoMapper;
import bakos.life_pm.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @PutMapping("/api/todo/update-position")
    public ResponseEntity<Void> updatePosition(@RequestBody UpdateTodoOrderRequest request) {
        todoService.updateTodoPosition(
                request.getMovedItemId(),
                request.getPreviousItemId(),
                request.getNextItemId(),
                request.getNewColumnId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/todo/create")
    public TodoDto createColumn(@RequestBody CreateTodoRequest request) {
        return TodoMapper.INSTANCE.toDto(todoService.createTodo(request.getName(), request.getColumnId()));
    }

    @DeleteMapping("/api/todo/delete/{id}")
    public ResponseEntity<Void> createColumn(@PathVariable(name = "id") UUID id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }


}
