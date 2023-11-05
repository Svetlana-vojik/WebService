package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.User;

import java.util.List;

public interface UserRepository {
    User createOrUpdateUser(User user);

    List<User> findAllUsers();

    void delete(int id);

    User findById(int id);

    User findByEmailAndPassword(String email, String password);
}