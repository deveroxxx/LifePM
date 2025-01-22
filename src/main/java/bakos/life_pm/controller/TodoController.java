package bakos.life_pm.controller;

import bakos.life_pm.dto.request.UpdateTodoOrderRequest;
import bakos.life_pm.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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


}
