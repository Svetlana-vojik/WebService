package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByEmailAndPassword(String email, String password);

    Optional<User> findByEmail(String email);
}