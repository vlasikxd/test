package com.bank.authorization.service;

import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Сервис для {@link  UserDetails}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthUserDetailService implements UserDetailsService {

    UserService service;

    UserMapper mapper;

    /**
     * @param username {@link String}
     * @return {@link UserDetails}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity user = mapper.toEntity(
                service.readByUserName(username)
        );

        return new UserEntity(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
