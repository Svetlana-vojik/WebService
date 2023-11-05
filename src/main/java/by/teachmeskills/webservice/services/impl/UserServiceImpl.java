package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.converters.UserConverter;
import by.teachmeskills.webservice.dto.LoginUserDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.exceptions.AuthorizationException;
import by.teachmeskills.webservice.repositories.UserRepository;
import by.teachmeskills.webservice.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAllUsers().stream().map(userConverter::toDto).toList();
    }

    @Override
    public UserDto getUserById(int id) {
        User user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d not found", id)));
        return userConverter.toDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userConverter.fromDto(userDto);
        user = userRepository.createOrUpdateUser(user);
        return userConverter.toDto(user);
    }

    @Override
    public UserDto loginUser(LoginUserDto userDto) throws AuthorizationException {
        User user = Optional.ofNullable(userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword()))
                .orElseThrow(() -> new AuthorizationException(String.format("User with email %s not registered", userDto.getEmail())));
        return userConverter.toDto(userRepository.createOrUpdateUser(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        User user = Optional.ofNullable(userRepository.findById(userDto.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %d not found", userDto.getId())));
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setBirthday(userDto.getBirthday());
        user.setBalance(userDto.getBalance());
        user.setAddress(userDto.getAddress());
        return userConverter.toDto(userRepository.createOrUpdateUser(user));
    }

    @Override
    public void deleteUser(int id) {
        userRepository.delete(id);
    }
}