package bakos.life_pm.service.strategy;


import bakos.life_pm.entity.FileAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface StorageStrategy {
    List<FileAttachment> saveAllFiles(List<MultipartFile> files, UUID parentId);
}
