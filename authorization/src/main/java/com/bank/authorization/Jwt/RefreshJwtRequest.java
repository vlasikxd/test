package com.bank.authorization.Jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * Запрос на обновление токена.
 */
@Getter
@Setter
public class RefreshJwtRequest {

    private String refreshToken;
}
