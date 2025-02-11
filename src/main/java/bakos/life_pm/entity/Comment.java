package bakos.life_pm.entity;

import bakos.life_pm.enums.EntityType;
import bakos.life_pm.service.UserNameResolver;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@EntityListeners(UserNameResolver.class)
@Table(name = "comments")
public class Comment extends WithUserNameAndWithTs implements Serializable {

    @Column(nullable = false)
    private UUID parentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityType parentType;

    @Column
    private String content;
}
