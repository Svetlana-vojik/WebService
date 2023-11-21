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
public class RefreshJwtRequestDto {
    @NotBlank(message = "рефреш-токен не должен быть пустым")
    String refreshToken;
}