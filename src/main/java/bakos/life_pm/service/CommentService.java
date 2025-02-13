package bakos.life_pm.service;

import bakos.life_pm.entity.Comment;
import bakos.life_pm.enums.EntityType;
import bakos.life_pm.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository, FileAttachmentService fileAttachmentService) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment addComment(UUID parentId, EntityType parentType, String content) {
        // FIXME: user ownership validation
        Comment comment = new Comment();
        comment.setParentId(parentId);
        comment.setParentType(parentType);
        comment.setContent(content);
        comment.setUserName(Utils.getUserFromSecurityContext());
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(UUID parentId) {
        // FIXME: user ownership validation
        return commentRepository.findByParentId(parentId);
    }
}
