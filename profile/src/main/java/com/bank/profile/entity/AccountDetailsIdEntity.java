package com.bank.profile.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity для таблицы account_details_id.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "account_details_id", schema = "profile")
public class AccountDetailsIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Column(name = "account_id")
    Long accountId;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id")
    ProfileEntity profile;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccountDetailsIdEntity accountDetailsId = (AccountDetailsIdEntity) o;
        return id.equals(accountDetailsId.id) &&
                accountId.equals(accountDetailsId.accountId) &&
                profile.equals(accountDetailsId.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, profile);
    }
}
