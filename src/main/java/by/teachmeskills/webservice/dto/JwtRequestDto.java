package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class JwtRequestDto {
    @NotBlank(message = "поле логина не должно быть пустым")
    private String login;
    @NotBlank(message = "поле пароль не должно быть пустым")
    private String password;
}