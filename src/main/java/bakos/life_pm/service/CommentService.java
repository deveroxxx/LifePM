package bakos.life_pm.service;

import bakos.life_pm.entity.Comment;
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
    public Comment addComment(UUID parentId, String content) {
        Comment comment = new Comment();
        comment.setParentId(parentId);
        comment.setContent(content);
        comment.setUserName(Utils.getUserFromSecurityContext());
        return commentRepository.save(comment);
    }

    public List<Comment> getComments(UUID parentId) {
        return commentRepository.findByParentId(parentId);
    }


    @Transactional
    public void editComment(UUID commentId, String content) {
        Comment comment = commentRepository.findByIdOrThrow(commentId);
        if (comment.getUserName().equals(Utils.getUserFromSecurityContext())) { //TODO: maybe i should move this logic next to the other validations
            comment.setContent(content);
        } else { //TODO: replace to specific exception
            throw new RuntimeException("User is not authorized to edit comment"  + commentId);
        }
    }

    @Transactional
    public void deleteComment(UUID commentId) {
        Comment comment = commentRepository.findByIdOrThrow(commentId);
        if (comment.getUserName().equals(Utils.getUserFromSecurityContext())) { //TODO: maybe i should move this logic next to the other validations
            commentRepository.deleteById(commentId);
        } else { //TODO: replace to specific exception
            throw new RuntimeException("User is not authorized to delete comment: " + commentId);
        }
    }
}
