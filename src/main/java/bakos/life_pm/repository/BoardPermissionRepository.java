package bakos.life_pm.repository;

import bakos.life_pm.entity.Board;
import bakos.life_pm.entity.BoardPermission;
import bakos.life_pm.enums.BoardPermissionEnum;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardPermissionRepository extends JpaRepository<BoardPermission, String> {
    Optional<BoardPermission> findByBoardIdAndUserName(UUID boardId, String userName);
    List<BoardPermission> findByUserName(String userName);
    List<BoardPermission> findByBoard(Board board);

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Query("SELECT p.permission FROM BoardPermission p " +
            "JOIN p.board b " +
            "WHERE b.id = :boardId AND p.userName = :userName")
    Optional<BoardPermissionEnum> findByBoardId(
            @Param("boardId") UUID boardId,
            @Param("userName") String userName
    );

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Query("SELECT p.permission FROM BoardPermission p " +
            "JOIN p.board b " +
            "JOIN b.columns c " +
            "WHERE c.id = :columnId AND p.userName = :userName")
    Optional<BoardPermissionEnum> findByColumnId(
            @Param("columnId") UUID columnId,
            @Param("userName") String userName
    );

    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    @Query("SELECT p.permission FROM BoardPermission p " +
            "JOIN p.board b " +
            "JOIN b.columns c " +
            "JOIN c.todos t " +
            "WHERE t.id = :todoId AND p.userName = :userName")
    Optional<BoardPermissionEnum> findByTodoId(
            @Param("todoId") UUID todoId,
            @Param("userName") String userName
    );

}