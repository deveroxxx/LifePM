package bakos.life_pm.mapper;

import bakos.life_pm.dto.response.BoardColumnDto;
import bakos.life_pm.entity.BoardColumn;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TodoMapper.class})
public interface BoardColumnMapper extends BaseMapper<BoardColumn, BoardColumnDto> {
    BoardColumnMapper INSTANCE = Mappers.getMapper(BoardColumnMapper.class);
}
