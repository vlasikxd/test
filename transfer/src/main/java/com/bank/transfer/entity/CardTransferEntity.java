package com.bank.transfer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Entity для таблицы "card_transfer".
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card_transfer", schema = "transfer")
public class CardTransferEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_details_id")
    private Long accountDetailsId;

    @Column(name = "card_number")
    private Long cardNumber;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "purpose")
    private String purpose;
}
