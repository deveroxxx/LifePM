package bakos.life_pm.mapper.static_mappers;

import bakos.life_pm.dto.response.FileInfoResponse;
import bakos.life_pm.entity.FileAttachment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileAttachmentMapper {

    public static FileInfoResponse toFileInfo(FileAttachment fileAttachment) {
        FileInfoResponse response = new FileInfoResponse();
        response.setId(fileAttachment.getId());
        response.setName(fileAttachment.getFileName());
        response.setType(fileAttachment.getFileType());
        return response;
    }

    public static FileInfoResponse toTodoImageInfo(FileAttachment fa) {
        FileInfoResponse response = toFileInfo(fa);
        response.setUrl("/api/todos/" + fa.getParentId() + "/images/" + fa.getId() + "/" + fa.getFileName());
        return response;
    }

}
