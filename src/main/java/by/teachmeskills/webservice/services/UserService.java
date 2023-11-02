package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.LoginUserDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.exceptions.AuthorizationException;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

    UserDto getUserById(int id);

    UserDto createUser(UserDto userDto);

    UserDto loginUser(LoginUserDto userDto)throws AuthorizationException;;

    UserDto updateUser(UserDto userDto);

    void deleteUser(int id);
}