package bakos.life_pm.service.strategy;


import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.enums.StorageType;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public sealed interface StorageStrategy permits FileAttachmentDbStrategy, FileAttachmentFileSystemStrategy, FileAttachmentMinioStrategy {
    FileAttachment saveFile(MultipartFile file, UUID parentId);
    InputStream downloadFile(FileAttachment file);
    StorageType type();
}
