package bakos.life_pm.mapper.static_mappers;

import bakos.life_pm.dto.response.FileInfoResponse;
import bakos.life_pm.entity.FileAttachment;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileAttachmentMapper {

    public static FileInfoResponse toTodoImageInfo(FileAttachment fa) {
        return new FileInfoResponse(
                fa.getId(),
                fa.getFileName(),
                fa.getFileType(),
                "/api/todos/" + fa.getParentId() + "/images/" + fa.getId() + "/" + fa.getFileName());
    }

}
