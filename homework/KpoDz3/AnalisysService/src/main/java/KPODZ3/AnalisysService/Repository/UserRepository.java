package KPODZ3.AnalisysService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import KPODZ3.AnalisysService.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
