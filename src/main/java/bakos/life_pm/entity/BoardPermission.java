package bakos.life_pm.entity;

import bakos.life_pm.enums.BoardPermissionEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "board_permissions", uniqueConstraints = {@UniqueConstraint(columnNames = {"board_id", "user_name"})})
public class BoardPermission extends WithUserNameAndWithTs {

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardPermissionEnum permission;

}