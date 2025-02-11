package bakos.life_pm.service;

import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.service.strategy.FileAttachmentDbStrategy;
import bakos.life_pm.service.strategy.FileAttachmentFileSystemStrategy;
import bakos.life_pm.service.strategy.StorageStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Component
public class FileAttachmentService implements StorageStrategy {

    private final FileAttachmentDbStrategy fileStorageStrategy;
    private final FileAttachmentFileSystemStrategy databaseStorageStrategy;

    public FileAttachmentService(FileAttachmentDbStrategy fileStorageStrategy, FileAttachmentFileSystemStrategy databaseStorageStrategy) {
        this.fileStorageStrategy = fileStorageStrategy;
        this.databaseStorageStrategy = databaseStorageStrategy;
    }

    public StorageStrategy getStrategy() {
        return databaseStorageStrategy; // TODO: add config from app or something
    }

    @Override
    public List<FileAttachment> saveAllFiles(List<MultipartFile> files, UUID parentId) {
        return getStrategy().saveAllFiles(files, parentId);
    }
}
