package bakos.life_pm.mapper;

import bakos.life_pm.dto.response.TodoTagDto;
import bakos.life_pm.entity.TodoTag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper extends BaseMapper<TodoTag, TodoTagDto> {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
}
