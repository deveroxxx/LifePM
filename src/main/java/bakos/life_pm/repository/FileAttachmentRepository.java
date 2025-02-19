package bakos.life_pm.repository;

import bakos.life_pm.entity.FileAttachment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.UUID;

public interface FileAttachmentRepository extends ListCrudRepository<FileAttachment, UUID> {

    default FileAttachment findByIdOrThrow(UUID id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException("FileAttachment with id " + id + " not found"));
    }

    List<FileAttachment> getFileAttachmentByParentId(UUID parentId);


}
