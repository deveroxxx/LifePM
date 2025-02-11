package bakos.life_pm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class WithUserName extends BaseEntity implements CustomerRelated {

    @Column(name = "user_name")
    private String userName;

}
