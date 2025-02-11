package bakos.life_pm.enums;

import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Comment;
import bakos.life_pm.entity.Todo;
import lombok.Getter;

@Getter
public enum EntityType {
    TODO(Todo.class),
    BOARD_COLUMN(BoardColumn.class),
    BOARD(Board.class),
    COMMENT(Comment.class);

    private final Class<?> entityClass;

    EntityType(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

}
