package com.bank.authorization.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity для таблицы users.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "auth")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "role")
    String role;

    @NotNull
    @Column(name = "profile_id")
    Long profileId;

    @NotNull
    @Column(name = "password")
    String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserEntity user = (UserEntity) o;
        return id.equals(user.id) && role.equals(user.role) &&
                profileId.equals(user.profileId) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role, profileId, password);
    }
}
