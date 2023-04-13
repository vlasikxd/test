package com.bank.authorization.dto;

import com.bank.authorization.entity.Role;
import com.bank.authorization.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;



import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ДТО для {@link UserEntity}.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto implements Serializable {

    Long id;

    @NotNull
    String username;

    @NotNull
    Role role;

    @NotNull
    Long profileId;

    @NotNull
    String password;
}
