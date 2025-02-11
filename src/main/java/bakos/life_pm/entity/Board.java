package bakos.life_pm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "boards")
@NoArgsConstructor
public class Board extends WithUserNameAndWithTs {

    public Board(String name) {
        this.name = name;
    }

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardColumn> columns = new ArrayList<>();

    @Column(nullable = false)
    private boolean archived = false;

    @Column(nullable = false)
    private Integer position;
}