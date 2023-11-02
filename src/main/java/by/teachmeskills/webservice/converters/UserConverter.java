package by.teachmeskills.webservice.converters;

import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserConverter {

    private final OrderConverter orderConverter;

    public UserDto toDto(User user) {
        return Optional.ofNullable(user).map(u -> UserDto.builder()
                        .id(u.getId())
                        .email(u.getEmail())
                        .password(u.getPassword())
                        .name(u.getName())
                        .surname(u.getSurname())
                        .birthday(u.getBirthday())
                        .balance(u.getBalance())
                        .address(u.getAddress())
                        .orders(Optional.ofNullable(u.getOrder()).map(o -> o
                                .stream().map(orderConverter::toDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }

    public User fromDto(UserDto userDto) {
        return Optional.ofNullable(userDto).map(u -> User.builder()
                        .email(u.getEmail())
                        .password(u.getPassword())
                        .name(u.getName())
                        .surname(u.getSurname())
                        .birthday(u.getBirthday())
                        .balance(u.getBalance())
                        .address(u.getAddress())
                        .order(Optional.ofNullable(u.getOrders()).map(o -> o
                                .stream().map(orderConverter::fromDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }
}