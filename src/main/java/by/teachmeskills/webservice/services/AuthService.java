package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.JwtRequestDto;
import by.teachmeskills.webservice.dto.JwtResponseDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.exceptions.AuthorizationException;
import lombok.NonNull;

import java.util.Optional;

public interface AuthService {
    JwtResponseDto login(@NonNull JwtRequestDto jwtRequestDto) throws AuthorizationException;

    JwtResponseDto getAccessToken(@NonNull String refreshToken);

    JwtResponseDto refresh(@NonNull String refreshToken) throws AuthorizationException;

    Optional<UserDto> getPrincipal();
}