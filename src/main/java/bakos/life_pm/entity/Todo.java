package bakos.life_pm.entity;


import bakos.life_pm.enums.Priority;
import bakos.life_pm.enums.TodoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "todos")
public class Todo extends TimestampedEntity implements CustomerRelated {

    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoType todoType;

    @Column(nullable = false)
    private boolean archived = false;

    @ManyToMany
    @JoinTable(
            name = "todo_tags",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TodoTag> todoTags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "board_column_id", nullable = false)
    private BoardColumn boardColumn;

    @Column(nullable = false)
    private Integer position;

    @ManyToMany
    @JoinTable(
            name = "todo_parents",
            joinColumns = @JoinColumn(name = "todo_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private List<Todo> parents = new ArrayList<>();
}