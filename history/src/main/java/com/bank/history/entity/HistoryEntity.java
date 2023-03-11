package com.bank.history.entity;

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

/**
 * Entity для таблицы history.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "history", schema = "history")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "transfer_audit_id")
    Long transferAuditId;

    @NotNull
    @Column(name = "profile_audit_id")
    Long profileAuditId;

    @NotNull
    @Column(name = "account_audit_id")
    Long accountAuditId;

    @NotNull
    @Column(name = "anti_fraud_audit_id")
    Long antiFraudAuditId;

    @NotNull
    @Column(name = "public_bank_info_audit_id")
    Long publicBankInfoAuditId;

    @NotNull
    @Column(name = "authorization_audit_id")
    Long authorizationAuditId;
}
