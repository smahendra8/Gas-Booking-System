package gasproject.repositories;

import gasproject.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByMobileNumber(String mobileNumber);
    UserEntity existsByEmail(String email);
boolean existsByCustomerId(String customerId);
UserEntity findByCustomerId(String customerId);

}
