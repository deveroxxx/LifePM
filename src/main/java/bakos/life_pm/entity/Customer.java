package bakos.life_pm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Customers are "detached" from the other entities
 * Current reasoning is makes testing, easier the code a bit more cleaner and more performant.
 * Also, we probably never delete a user, but if we do we have to manually clean up the connected entities
 */
@Entity
@Getter
@Setter
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
public class Customer implements Serializable {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @CreatedDate
    protected LocalDateTime createdOn;

    @LastModifiedDate
    protected LocalDateTime updatedOn;
}
