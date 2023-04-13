package com.bank.authorization.controller;

import com.bank.authorization.Jwt.JwtRequest;
import com.bank.authorization.Jwt.JwtResponse;
import com.bank.authorization.Jwt.RefreshJwtRequest;
import com.bank.authorization.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * @param jwtRequest {@link JwtRequest}.
     * @return {@link ResponseEntity<JwtResponse>}.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        final JwtResponse token = authService.login(jwtRequest);
        return ResponseEntity.ok(token);
    }

    /**
     * @param refreshJwtRequest {@link RefreshJwtRequest}.
     * @return {@link ResponseEntity<JwtResponse>}.
     */
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        final JwtResponse token = authService.getAccessToken(refreshJwtRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    /**
     * @param refreshJwtRequest {@link RefreshJwtRequest}.
     * @return {@link ResponseEntity<JwtResponse>}.
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest refreshJwtRequest) {
        final JwtResponse token = authService.refresh(refreshJwtRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
