package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.config.JwtProvider;
import by.teachmeskills.webservice.dto.JwtRequestDto;
import by.teachmeskills.webservice.dto.JwtResponseDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.converters.UserConverter;
import by.teachmeskills.webservice.entities.Token;
import by.teachmeskills.webservice.entities.User;
import by.teachmeskills.webservice.exceptions.AuthorizationException;
import by.teachmeskills.webservice.repositories.TokenRepository;
import by.teachmeskills.webservice.repositories.UserRepository;
import by.teachmeskills.webservice.services.AuthService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    @Override
    public JwtResponseDto login(@NonNull JwtRequestDto jwtRequestDto) throws AuthorizationException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(jwtRequestDto.getLogin()).orElseThrow(() -> new AuthorizationException("пользователь не найден")));
        if (user.isPresent() && passwordEncoder.matches(jwtRequestDto.getPassword(), user.get().getPassword())) {
            String accessToken = jwtProvider.generateAccessToken(jwtRequestDto.getLogin());
            String refreshToken = jwtProvider.generateRefreshToken(jwtRequestDto.getLogin());
            Token refreshTokenFromRepository = tokenRepository.findByUsername(jwtRequestDto.getLogin());
            if (refreshTokenFromRepository != null) {
                refreshTokenFromRepository.setToken(refreshToken);
                tokenRepository.save(refreshTokenFromRepository);
            } else {
                tokenRepository.save(Token.builder().id(0).token(refreshToken).username(user.get().getEmail()).build());
            }
            return new JwtResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthorizationException("Неправильный пароль");
        }
    }

    @Override
    public JwtResponseDto getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            String repositoryToken = tokenRepository.findByUsername(login).getToken();
            if (repositoryToken != null && repositoryToken.equals(refreshToken)) {
                String accessToken = jwtProvider.generateAccessToken(login);
                return new JwtResponseDto(accessToken, null);
            }
        }
        return new JwtResponseDto(null, null);
    }

    @Override
    public JwtResponseDto refresh(@NonNull String refreshToken) throws AuthorizationException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            String login = claims.getSubject();
            Token repositoryToken = tokenRepository.findByUsername(login);
            if (repositoryToken != null && repositoryToken.getToken().equals(refreshToken)) {
                String accessToken = jwtProvider.generateAccessToken(login);
                String newRefreshToken = jwtProvider.generateRefreshToken(login);
                repositoryToken.setToken(newRefreshToken);
                tokenRepository.save(repositoryToken);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw new AuthorizationException("Невалидный JWT токен");
    }

    @Override
    public Optional<UserDto> getPrincipal() {
        return userRepository.findByEmail(((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().
                getPrincipal()).getUsername()).map(userConverter::toDto);
    }
}