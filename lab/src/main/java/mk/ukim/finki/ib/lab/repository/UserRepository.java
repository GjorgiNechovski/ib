package mk.ukim.finki.ib.lab.repository;

import mk.ukim.finki.ib.lab.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
