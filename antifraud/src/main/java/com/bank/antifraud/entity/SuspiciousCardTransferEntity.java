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
 * Entity для таблицы suspicious_card_transfer
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "suspicious_card_transfer", schema = "anti_fraud")
public class SuspiciousCardTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Column(name = "card_transfer_id")
    Long cardTransferId;

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
