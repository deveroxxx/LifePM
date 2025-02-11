package bakos.life_pm.service;

import bakos.life_pm.entity.Comment;
import bakos.life_pm.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final FileAttachmentService fileAttachmentService;

    public CommentService(CommentRepository commentRepository, FileAttachmentService fileAttachmentService) {
        this.commentRepository = commentRepository;
        this.fileAttachmentService = fileAttachmentService;
    }

    @Transactional
    public Comment addComment(UUID parentId, String content, List<MultipartFile> files) {
        // FIXME: user ownership validation
        Comment comment = new Comment();
        comment.setParentId(parentId);
        comment.setContent(content);
        comment.setUserName(Utils.getUserFromSecurityContext());
        Comment savedComment = commentRepository.save(comment);
        fileAttachmentService.saveAllFiles(files, parentId);
        return savedComment;
    }

    public List<Comment> getComments(UUID parentId) {
        // FIXME: user ownership validation
        return commentRepository.findByParentId(parentId);
    }
}
