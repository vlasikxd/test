package com.bank.authorization.Jwt;

import com.bank.authorization.entity.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(getRole(claims));
        jwtInfoToken.setProfileId(
                claims.get("profile_id", Long.class)
        );

        return jwtInfoToken;
    }

    private static Role getRole(Claims claims) {
        return claims.get("roles", Role.class);
    }
}
