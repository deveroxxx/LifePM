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
public class Board extends Timestamped {

    public Board(String name, String description) {
        this.name = name;
        this.description = description;
        this.archived = false;
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
}