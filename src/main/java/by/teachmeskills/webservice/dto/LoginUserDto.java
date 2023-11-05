package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@SuperBuilder
@NoArgsConstructor
public class LoginUserDto {
    @NotNull(message = "email не должен быть пустым")
    @NotBlank(message = "email не должен быть пустым")
    @Email(message = "Некорректный адрес электронной почты.")
    private String email;

    @NotNull(message = "пароль не должен быть пустым")
    @NotBlank(message = "пароль не должен быть пустым")
    @Size(min = 4, max = 10, message = "длина пароля должна быть от 4 до 10 символов")
    private String password;
}