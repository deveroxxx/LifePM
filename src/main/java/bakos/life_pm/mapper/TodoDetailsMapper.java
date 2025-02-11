package bakos.life_pm.mapper;

import bakos.life_pm.dto.response.CommentDto;
import bakos.life_pm.dto.response.TodoDetailsDto;
import bakos.life_pm.entity.Comment;
import bakos.life_pm.entity.Todo;
import bakos.life_pm.repository.CommentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TodoMapper.class, CommentMapper.class})
public abstract class TodoDetailsMapper implements BaseMapper<Todo, TodoDetailsDto>{

    public static TodoDetailsMapper getMapper() {
        return Mappers.getMapper(TodoDetailsMapper.class);
    }

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Mapping(target = "comments", expression = "java(mapComments(todo))")
    public abstract TodoDetailsDto toDto(Todo todo);

    List<CommentDto> mapComments(Todo todo) {
        List<Comment> comments = commentRepository.findByParentId(todo.getId());
        return comments.stream()
                .map(commentMapper::toDto) // Use CommentMapper to convert Comment -> CommentDto
                .collect(Collectors.toList());
    }
}