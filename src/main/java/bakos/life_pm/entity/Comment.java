package bakos.life_pm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends WithUserNameAndWithTs implements Serializable {

    @Column(nullable = false)
    private UUID parentId;

    @Column
    private String content;
}
