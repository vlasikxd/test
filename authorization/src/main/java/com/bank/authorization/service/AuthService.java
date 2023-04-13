package com.bank.authorization.service;

import com.bank.authorization.Jwt.JwtProvider;
import com.bank.authorization.Jwt.JwtRequest;
import com.bank.authorization.Jwt.JwtResponse;
import com.bank.authorization.entity.UserEntity;
import com.bank.common.exception.ClientAccessDeniedException;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис валидации пользователя и выдачи access и refresh токенов.
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    AuthUserDetailService service;
    Map<String, String> refreshStorage = new HashMap<>();
    JwtProvider jwtProvider;
    PasswordEncoder passwordEncoder;

    /**
     * @param authRequest {@link JwtRequest}.
     * @return {@link JwtResponse}.
     */

    /*
        TODO Для хранения рефреш токена используется HashMap лишь для упрощения. Лучше использовать какое-нибудь
         постоянное хранилище, например Redis.
    */
    public JwtResponse login(@NonNull JwtRequest authRequest) {

        final UserEntity user = (UserEntity) service.loadUserByUsername(
                authRequest.getUsername()
        );

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);

            refreshStorage.put(user.getUsername(), refreshToken);

            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new ClientAccessDeniedException("Неправильный пароль");
        }
    }

    /**
     * @param refreshToken {@link String}.
     * @return {@link JwtResponse}.
     */
    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = (UserEntity) service.loadUserByUsername(username);
                final String accessToken = jwtProvider.generateAccessToken(user);

                return new JwtResponse(accessToken, null);
            }
        }

        return new JwtResponse(null, null);
    }

    /**
     * @param refreshToken {@link String}.
     * @return {@link JwtResponse}.
     */
    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = (UserEntity) service.loadUserByUsername(username);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                refreshStorage.put(user.getUsername(), newRefreshToken);

                return new JwtResponse(accessToken, newRefreshToken);
            }
        }

        throw new ClientAccessDeniedException("Невалидный JWT токен");
    }
}
