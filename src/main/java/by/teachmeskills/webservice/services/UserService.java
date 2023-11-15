package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.LoginUserDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.exceptions.AuthorizationException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(int id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    void deleteUser(int id);

    void register(UserDto userDto);

    Optional<User> findByLogin(String login);
}