package com.bank.authorization.controller;

import com.bank.authorization.Jwt.JwtRequest;
import com.bank.authorization.Jwt.JwtResponse;
import com.bank.authorization.Jwt.RefreshJwtRequest;
import com.bank.authorization.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Контроллер для авторизации и генерации токенов")
public class AuthController {

    private final AuthService authService;

    /**
     * @param jwtRequest {@link JwtRequest}.
     * @return {@link ResponseEntity<JwtResponse>}.
     */
    @PostMapping("/login")
    @Operation(summary = "Авторизация")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        final JwtResponse token = authService.login(jwtRequest);
        return ResponseEntity.ok(token);
    }

    /**
     * @param refreshJwtRequest {@link RefreshJwtRequest}.
     * @return {@link ResponseEntity<JwtResponse>}.
     */
    @PostMapping("/token")
    @Operation(summary = "Создание токена")
    public ResponseEntity<JwtResponse> createAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        final JwtResponse token = authService.getAccessToken(refreshJwtRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    /**
     * @param refreshJwtRequest {@link RefreshJwtRequest}.
     * @return {@link ResponseEntity<JwtResponse>}.
     */
    @PostMapping("/refresh")
    @Operation(summary = "Обновление токена")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        final JwtResponse token = authService.refresh(refreshJwtRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
