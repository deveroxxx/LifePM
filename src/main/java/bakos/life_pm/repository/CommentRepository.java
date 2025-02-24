package bakos.life_pm.repository;

import bakos.life_pm.entity.Comment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends ListCrudRepository<Comment, UUID> {

    List<Comment> findByParentId(UUID parentId);
    default Comment findByIdOrThrow(UUID id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found with id:" + id));
    }
}
