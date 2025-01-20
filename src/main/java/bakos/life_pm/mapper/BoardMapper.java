package bakos.life_pm.mapper;

import bakos.life_pm.dto.BoardDto;
import bakos.life_pm.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BoardColumnMapper.class})
public interface BoardMapper extends BaseMapper<Board, BoardDto> {
    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

}