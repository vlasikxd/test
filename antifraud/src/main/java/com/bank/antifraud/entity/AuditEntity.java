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
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Entity для таблицы audit
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "audit", schema = "anti_fraud")
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @Size(max = 40)
    @Column(name = "entity_type")
    String entityType;

    @NotNull
    @Size(max = 255)
    @Column(name = "operation_type")
    String operationType;

    @NotNull
    @Size(max = 255)
    @Column(name = "created_by")
    String createdBy;

    @Size(max = 255)
    @Column(name = "modified_by")
    String modifiedBy;

    @NotNull
    @Column(name = "created_at")
    Timestamp createdAt;

    @Column(name = "modified_at")
    Timestamp modifiedAt;

    @Column(name = "new_entity_json")
    String newEntityJson;

    @NotNull
    @Column(name = "entity_json")
    String entityJson;
}
