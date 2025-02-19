package bakos.life_pm.service.strategy;

import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.repository.FileAttachmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static bakos.life_pm.enums.StorageStrategy.DATABASE;

@Component
public class FileAttachmentDbStrategy implements StorageStrategy {

    private final FileAttachmentRepository fileAttachmentRepository;

    public FileAttachmentDbStrategy(FileAttachmentRepository fileAttachmentRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
    }

    @Override
    public FileAttachment saveFile(MultipartFile file, UUID parentId) {
        try {
            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setStorageStrategy(DATABASE);
            fileAttachment.setFileType(file.getContentType());
            fileAttachment.setFileName(file.getOriginalFilename());
            fileAttachment.setFileData(file.getBytes());
            fileAttachment.setParentId(parentId);
            return fileAttachmentRepository.save(fileAttachment);
        } catch (IOException e) {
            throw new RuntimeException("Could not save file", e);
        }
    }

    @Override
    public InputStream downloadFile(FileAttachment file) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
