package bakos.life_pm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_columns")
public class BoardColumn extends TimestampedEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "boardColumn", cascade = CascadeType.ALL, orphanRemoval = true) //TODO: do i need orphan removal?
    private List<Todo> todos = new ArrayList<>();

    @Column(nullable = false)
    private Integer position;

    @Transient
    private String userName;

    @PostLoad
    private void initUserName() {
        this.userName = this.getBoard().getUserName();
    }
}