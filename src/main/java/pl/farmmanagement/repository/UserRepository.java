package pl.farmmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.farmmanagement.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUserName(String name);
}
