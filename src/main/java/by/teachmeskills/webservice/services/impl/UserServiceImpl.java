package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.converters.OrderConverter;
import by.teachmeskills.webservice.converters.UserConverter;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.entities.Role;
import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.repositories.OrderRepository;
import by.teachmeskills.webservice.repositories.UserRepository;
import by.teachmeskills.webservice.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, OrderRepository orderRepository, OrderConverter orderConverter, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userConverter::toDto).toList();
    }

    @Override
    public UserDto getUserById(int id) {
        return userConverter.toDto(userRepository.findById(id));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userConverter.fromDto(userDto);
        user = userRepository.save(user);
        return userConverter.toDto(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
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
        return userConverter.toDto(userRepository.save(user));
    }

    @Override
    public void register(UserDto userDto) {
        {
            User user = new User();
            user.setName(userDto.getName());
            user.setSurname(userDto.getSurname());
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setBirthday(userDto.getBirthday());
            user.setOrder(userDto.getOrders().stream().map(orderConverter::fromDto).toList());
                      user.setRoles(List.of(Role.builder().id(2).name("USER").build()));
            userRepository.save(user);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByEmail(login);
    }
}