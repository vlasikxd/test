package com.bank.authorization.Jwt;

import lombok.Getter;
import lombok.Setter;

/**
 * Запрос на аутенитификацию.
 */
@Setter
@Getter
public class JwtRequest {

    private String username;
    private String password;
}
