package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findByUsername(String username);
}