package bakos.life_pm.service.strategy;

import bakos.life_pm.entity.FileAttachment;
import bakos.life_pm.repository.FileAttachmentRepository;
import bakos.life_pm.service.Utils;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import static bakos.life_pm.enums.StorageStrategy.MINIO;
import static org.springframework.util.ResourceUtils.toURL;

@Component
public class FileAttachmentMinioStrategy implements StorageStrategy {

    private final MinioClient minioClient;
    private final FileAttachmentRepository fileAttachmentRepository;

    @Value("${minio.bucket.attachments}")
    private String attachmentBucket;

    public FileAttachmentMinioStrategy(MinioClient minioClient, FileAttachmentRepository fileAttachmentRepository) {
        this.minioClient = minioClient;
        this.fileAttachmentRepository = fileAttachmentRepository;
    }


    @Override
    public FileAttachment saveFile(MultipartFile file, UUID parentId) {
        try {
            String objectKey  = Utils.getUserFromSecurityContext() + "/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(attachmentBucket)
                            .object(objectKey)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            FileAttachment fileAttachment = new FileAttachment();
            fileAttachment.setStorageStrategy(MINIO);
            fileAttachment.setFileType(file.getContentType());
            fileAttachment.setFileName(file.getOriginalFilename());
            fileAttachment.setFilePath(objectKey);
            fileAttachment.setParentId(parentId);
            return fileAttachmentRepository.save(fileAttachment);
        } catch (Exception e) {
            throw new RuntimeException("Could not save file", e);
        }
    }

    @Override
    public InputStream downloadFile(FileAttachment file) {
        try {
            String preSignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(attachmentBucket)
                            .object(file.getFilePath())
                            .method(Method.GET)
                            .expiry(60) // 1-minute expiry
                            .build()
            );
            URL url = toURL(preSignedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            return connection.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("Could not download file", e);
        }
    }

}
