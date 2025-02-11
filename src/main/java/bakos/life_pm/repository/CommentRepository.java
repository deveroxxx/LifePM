package bakos.life_pm.repository;

import bakos.life_pm.entity.Comment;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends ListCrudRepository<Comment, UUID> {

    List<Comment> findByParentId(UUID parentId);
}
