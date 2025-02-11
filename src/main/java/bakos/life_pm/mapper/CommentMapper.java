package bakos.life_pm.mapper;

import bakos.life_pm.dto.response.CommentDto;
import bakos.life_pm.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<Comment, CommentDto>{
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
}