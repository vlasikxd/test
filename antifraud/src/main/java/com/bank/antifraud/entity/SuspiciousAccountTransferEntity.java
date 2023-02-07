package com.bank.antifraud.entity;

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
 * Entity для таблицы suspicious_account_transfer
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "suspicious_account_transfer", schema = "anti_fraud")
public class SuspiciousAccountTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Column(name = "account_transfer_id")
    Long accountTransferId;

    @NotNull
    @Column(name = "is_blocked")
    Boolean isBlocked;

    @NotNull
    @Column(name = "is_suspicious")
    Boolean isSuspicious;

    @Column(name = "blocked_reason")
    String blockedReason;

    @NotNull
    @Column(name = "suspicious_reason")
    String suspiciousReason;
}
