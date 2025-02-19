package bakos.life_pm.service;

import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.repository.FileAttachmentRepository;
import bakos.life_pm.service.strategy.FileAttachmentDbStrategy;
import bakos.life_pm.service.strategy.FileAttachmentFileSystemStrategy;
import bakos.life_pm.service.strategy.FileAttachmentMinioStrategy;
import bakos.life_pm.service.strategy.StorageStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Component
public class FileAttachmentService {

    private final FileAttachmentDbStrategy fileStorageStrategy;
    private final FileAttachmentFileSystemStrategy databaseStorageStrategy;
    private final FileAttachmentMinioStrategy minioStorageStrategy;
    private final FileAttachmentRepository fileAttachmentRepository;

    public FileAttachmentService(FileAttachmentDbStrategy fileStorageStrategy, FileAttachmentFileSystemStrategy databaseStorageStrategy, FileAttachmentMinioStrategy minioStorageStrategy, FileAttachmentRepository fileAttachmentRepository) {
        this.fileStorageStrategy = fileStorageStrategy;
        this.databaseStorageStrategy = databaseStorageStrategy;
        this.minioStorageStrategy = minioStorageStrategy;
        this.fileAttachmentRepository = fileAttachmentRepository;
    }

    public StorageStrategy getStrategy() {
        return minioStorageStrategy; // TODO: add config from app or something
    }


    public FileAttachment saveFile(MultipartFile file, UUID parentId) {
        return getStrategy().saveFile(file, parentId);
    }

    public InputStream downloadFile(UUID fileId) {
        return getStrategy().downloadFile(fileAttachmentRepository.findByIdOrThrow(fileId));
    }

    //TODO: either put this into a valid annotation or just use it
    public boolean isValidRequest(UUID fileId, UUID parentId) {
        FileAttachment attachment = fileAttachmentRepository.findByIdOrThrow(fileId);
        return attachment.getParentId().equals(parentId);
    }

    public List<FileAttachment> getFileAttachmentByParentId(UUID parentId) {
        return fileAttachmentRepository.getFileAttachmentByParentId(parentId);
    }
}
