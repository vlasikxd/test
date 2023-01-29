package com.bank.authorization.dto;

import com.bank.authorization.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ДТО для {@link UserEntity}.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {

    private Long id;

    @NotNull
    private String role;

    @NotNull
    private Long profileId;

    @NotNull
    private String password;
}
