package bakos.life_pm.service;

import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.enums.StorageType;
import bakos.life_pm.exception.SystemConfigurationException;
import bakos.life_pm.repository.FileAttachmentRepository;
import bakos.life_pm.service.strategy.StorageStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class FileAttachmentService {

    private final FileAttachmentRepository fileAttachmentRepository;
    private final Map<StorageType, StorageStrategy> strategies;
    //TODO: -delete file (normal, or after we failed to insert into the database)
    //TODO: -cleanup mechanism, maybe some scheduled job to compare that do we have any file that not
    //TODO:  in the db but still present on the chosen storage.

    public FileAttachmentService(List<StorageStrategy> strategyBeans, FileAttachmentRepository fileAttachmentRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.strategies = strategyBeans.stream()
                .collect(java.util.stream.Collectors.toUnmodifiableMap(StorageStrategy::type, s -> s));
    }

    public StorageStrategy getDefaultStrategy() {
        return strategies.get(StorageType.MINIO); // TODO: add config from app or something
        // if we want to be sophisticated we should probably store the large file in an external storage (minio/s3)
        // or s3 and use pre-signed urls so we don't double download the files.
        // for smaller files we could save them to do db, or a separate db.
    }

    public FileAttachment saveFile(MultipartFile file, UUID parentId) {
        // TODO: we should encrypt the data, also validate the file for malicious things.
        // this is a complicated topic for a hobby project, but definitely important if we want enterprise grade security.
        // look into, SSE-KMS/SSE-S3, application level encryption etc.
        return getDefaultStrategy().saveFile(file, parentId);
    }

    public InputStream downloadFile(UUID fileId) {
        FileAttachment file = fileAttachmentRepository.findByIdOrThrow(fileId);
        StorageStrategy storageType = strategies.get(file.getStorageStrategy());
        if (storageType != null) {
            return storageType.downloadFile(file);
        } else {
            throw new SystemConfigurationException("File (" + fileId +") was requested for download," +
                    " but the saved configuration is currently not available: " + file.getStorageStrategy());
        }
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
