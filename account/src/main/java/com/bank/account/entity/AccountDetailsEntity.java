package com.bank.account.entity;

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
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entity для таблицы account_details.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "account", name = "account_details")
public class AccountDetailsEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "passport_id")
    Long passportId;

    @Column(name = "account_number")
    Long accountNumber;

    @Column(name = "bank_details_id")
    Long bankDetailsId;

    @Column
    BigDecimal money;

    @Column(name = "negative_balance")
    Boolean negativeBalance;

    @Column(name = "profile_id")
    Long profileId;

    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccountDetailsEntity accountDetails = (AccountDetailsEntity) o;
        return negativeBalance == accountDetails.negativeBalance &&
                Objects.equals(id, accountDetails.id) &&
                Objects.equals(passportId, accountDetails.passportId) &&
                Objects.equals(accountNumber, accountDetails.accountNumber) &&
                Objects.equals(bankDetailsId, accountDetails.bankDetailsId) &&
                Objects.equals(money, accountDetails.money) &&
                Objects.equals(profileId, accountDetails.profileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passportId, accountNumber, bankDetailsId, money, negativeBalance, profileId);
    }
}
