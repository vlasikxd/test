package com.bank.authorization.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Enum для списка ролей.
 */
@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    USER("USER");


    private final String value;


    @Override
    public String getAuthority() {
        return value;
    }
}
