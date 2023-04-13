package com.bank.authorization.Jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Ответ на успешную аутентификацию.
 */
@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
