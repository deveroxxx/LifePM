package bakos.life_pm.repository;

import bakos.life_pm.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String userName);

    default User findByUserNameOrThrow(String username) {
        return findByUserName(username).
                orElseThrow(() -> new EntityNotFoundException("User not found with username:" + username));
    }

}
