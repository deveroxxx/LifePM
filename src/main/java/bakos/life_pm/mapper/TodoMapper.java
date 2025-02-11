package bakos.life_pm.mapper;

import bakos.life_pm.dto.response.TodoDto;
import bakos.life_pm.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TagMapper.class})
public interface TodoMapper extends BaseMapper<Todo, TodoDto>{
    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);
}
