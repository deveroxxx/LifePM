package bakos.life_pm.service.strategy;

import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.repository.FileAttachmentRepository;
import bakos.life_pm.service.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FileAttachmentFileSystemStrategy implements StorageStrategy{

    private final FileAttachmentRepository fileAttachmentRepository;

    @Value("${files.root.location}")
    private String rootLocation;

    public FileAttachmentFileSystemStrategy(FileAttachmentRepository fileAttachmentRepository) {
        this.fileAttachmentRepository = fileAttachmentRepository;
    }

    private FileAttachment saveFileOnDisk(MultipartFile file, UUID parentId) {
        try {
            Path uploadPath = getUserFilePath();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + File.separator + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setFileType(file.getContentType());
            fileAttachment.setFileName(file.getOriginalFilename());
            fileAttachment.setFilePath(filePath.toString());
            fileAttachment.setParentId(parentId);
            return fileAttachment;
        } catch (IOException e) {
            throw new RuntimeException("Could not save file", e);
        }
    }


    @Override
    public List<FileAttachment> saveAllFiles(List<MultipartFile> files, UUID parentId) {
        if (files == null || files.isEmpty()) {
            return new ArrayList<>();
        }

        List<FileAttachment> fileAttachments = files.stream()
                .map(file -> saveFileOnDisk(file, parentId))
                .collect(Collectors.toList());
        return fileAttachmentRepository.saveAll(fileAttachments);
    }

    public Path getUserFilePath() {
        String userDirectory = Utils.getUserFromSecurityContext() + File.separator + "comments" + File.separator + "attachments" + File.separator;
        return Paths.get(rootLocation, userDirectory);
    }


}
