package com.bank.authorization;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {

    protected static final Long ONE = 1L;
    protected static final Long TWO = 2L;

    protected static final String ROLE_USER = "ROLE_USER";
    protected static final String ROLE_ADMIN = "ROLE_ADMIN";
    protected static final String PASSWORD = "password";
    protected static final String PASSWORD_ADMIN = "passwordAdmin";

    protected static UserDto getUserDto(Long id, String role, String password, Long profileId) {
        return UserDto.builder()
                .id(id)
                .role(role)
                .password(password)
                .profileId(profileId)
                .build();
    }

    protected static UserEntity getUser(Long id, String role, String password, Long profileId) {
        return new UserEntity(id, role, profileId, password);
    }

    protected static List<UserEntity> getUsers(UserEntity... users) {
        return List.of(users);
    }
}
