package bakos.life_pm.service.strategy;

import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.repository.FileAttachmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FileAttachmentDbStrategy implements StorageStrategy {

    private final FileAttachmentRepository fileAttachmentRepository;

    public FileAttachmentDbStrategy(FileAttachmentRepository fileAttachmentRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
    }

    private FileAttachment saveFileInDatabase(MultipartFile file, UUID parentId) {
        try {
            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setFileType(file.getContentType());
            fileAttachment.setFileName(file.getOriginalFilename());
            fileAttachment.setFileData(file.getBytes());
            fileAttachment.setParentId(parentId);
            return fileAttachment;
        } catch (IOException e) {
            throw new RuntimeException("Could not save file", e);
        }
    }

    @Override
    public List<FileAttachment> saveAllFiles(List<MultipartFile> files,  UUID parentId) {
        if (files == null || files.isEmpty()) {
            return List.of();
        }

        List<FileAttachment> fileAttachments = files.stream()
                .map(file -> saveFileInDatabase(file, parentId))
                .collect(Collectors.toList());

        return fileAttachmentRepository.saveAll(fileAttachments);
    }
}
