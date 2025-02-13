package bakos.life_pm.mapper.complex;

import bakos.life_pm.dto.response.CommentDto;
import bakos.life_pm.dto.response.TodoDetailsDto;
import bakos.life_pm.dto.response.TodoDto;
import bakos.life_pm.entity.Comment;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.mapper.CommentMapper;
import bakos.life_pm.mapper.TodoMapper;
import bakos.life_pm.repository.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoDetailsMapper {

    private final CommentRepository commentRepository;

    public TodoDetailsMapper(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public TodoDetailsDto toDto(Todo todo) {
        TodoDto baseDto = TodoMapper.INSTANCE.toDto(todo);
        if (baseDto == null) {
            return null;
        }
        TodoDetailsDto detailsDto = new TodoDetailsDto();
        BeanUtils.copyProperties(baseDto, detailsDto);
        detailsDto.setComments(mapComments(todo));
        return detailsDto;
    }

    List<CommentDto> mapComments(Todo todo) {
        List<Comment> comments = commentRepository.findByParentId(todo.getId());
        return comments.stream()
                .map(CommentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}