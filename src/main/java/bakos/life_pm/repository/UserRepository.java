package bakos.life_pm.repository;

import bakos.life_pm.entity.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ListCrudRepository<Customer, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    Optional<Customer> findByUserName(String userName);

    default Customer findByUserNameOrThrow(String username) {
        return findByUserName(username).
                orElseThrow(() -> new EntityNotFoundException("User not found with username:" + username));
    }

}
