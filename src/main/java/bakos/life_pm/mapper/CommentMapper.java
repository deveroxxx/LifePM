package bakos.life_pm.mapper;

import bakos.life_pm.dto.response.CommentDto;
import bakos.life_pm.entity.Comment;
import bakos.life_pm.service.Utils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper extends BaseMapper<Comment, CommentDto>{
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "owner", ignore = true)
    CommentDto toDtoWithoutOwner(Comment comment);

    default CommentDto toDto(Comment comment) {
        CommentDto dto = toDtoWithoutOwner(comment);
        dto.setOwner(Utils.getUserFromSecurityContext().equals(comment.getUserName()));
        return dto;
    }
}