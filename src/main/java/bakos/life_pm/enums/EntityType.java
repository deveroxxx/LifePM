package bakos.life_pm.enums;

import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardColumn;
import bakos.life_pm.entity.Comment;
import bakos.life_pm.entity.Todo;
import lombok.Getter;

@Getter
public enum EntityType {
    TODO(Todo.class, "todo"),
    BOARD_COLUMN(BoardColumn.class, "boardColumn"),
    BOARD(Board.class, "board"),
    COMMENT(Comment.class, "comment"),;

    private final Class<?> entityClass;
    private final String path;

    EntityType(Class<?> entityClass, String path) {
        this.entityClass = entityClass;
        this.path = path;
    }

}
