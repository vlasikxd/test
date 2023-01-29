package com.bank.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
@AllArgsConstructor
@RequiredArgsConstructor
@Table(schema = "account", name = "account_details")
public class AccountDetailsEntity {
    // TODO в этом пакете сделай AuditEntity.
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passport_id")
    private Long passportId;

    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "bank_details_id")
    private Long bankDetailsId;

    @Column
    private BigDecimal money;

    @Column(name = "negative_balance")
    private Boolean negativeBalance;

    @Column(name = "profile_id")
    private Long profileId;

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
