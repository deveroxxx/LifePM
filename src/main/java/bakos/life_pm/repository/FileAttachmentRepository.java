package bakos.life_pm.repository;

import bakos.life_pm.entity.FileAttachment;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface FileAttachmentRepository extends ListCrudRepository<FileAttachment, UUID> {
}
