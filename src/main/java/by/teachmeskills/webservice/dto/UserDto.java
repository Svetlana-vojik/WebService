package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {
    @NotNull(message = "email не должен быть пустым")
    @NotBlank(message = "email не должен быть пустым")
    @Email(message = "Некорректный адрес электронной почты.")
    private String email;

    @NotNull(message = "пароль не должен быть пустым")
    @NotBlank(message = "пароль не должен быть пустым")
    @Size(min = 4, max = 10, message = "длина пароля должна быть от 4 до 10 символов")
    private String password;

    @NotNull(message = "имя не должен быть пустым")
    @NotBlank(message = "имя не должно быть пустым")
    @Pattern(regexp = "[A-Za-z А-Яа-я]+", message = "Некорректное имя.")
    private String name;

    @NotNull(message = "фамилия не должен быть пустым")
    @NotBlank(message = "фамилия не должна быть пустой")
    @Pattern(regexp = "[A-Za-z А-Яа-я]+", message = "Некорректная фамилия.")
    private String surname;

    @NotNull(message = "дата рождения не должна быть пустой")
    @Past(message = "Некорректная дата.")
    private LocalDate birthday;

    private int balance;

    @NotNull(message = "адрес не должен быть пустым")
    @Pattern(regexp = "[A-Za-z А-Яа-я0-9\\d]+", message = "Некорректный адрес")
    private String address;

    private List<OrderDto> orders;
}