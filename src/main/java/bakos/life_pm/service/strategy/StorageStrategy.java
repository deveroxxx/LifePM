package bakos.life_pm.service.strategy;


import bakos.life_pm.entity.FileAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface StorageStrategy {
    FileAttachment saveFile(MultipartFile file, UUID parentId);
    InputStream downloadFile(FileAttachment file);
}
