package com.bank.authorization.supplier;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.UserEntity;

import java.util.List;

public class UserSupplier {
    public static UserDto getUserDto(Long id, String username, Role role, String password, Long profileId) {
        return new UserDto(id, username, role, profileId, password);
    }

    public static UserEntity getUser(Long id, String username, Role role, String password, Long profileId) {
        return new UserEntity(id, username, role, profileId, password);
    }

    public static List<UserEntity> getUsers(UserEntity... users) {
        return List.of(users);
    }
}
