package by.teachmeskills.webservice.repositories.impl;

import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public User createOrUpdateUser(User user) {
        return entityManager.merge(user);
    }

    @Override
    public List<User> findAllUsers() {
        return entityManager.createQuery("select u from User u ", User.class).getResultList();
    }

    @Override
    public void delete(int id) {
        User user = Optional.ofNullable(entityManager.find(User.class, id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d not found", id)));
        entityManager.remove(user);
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.email=:email and u.password=:password", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getSingleResult();
    }
}
