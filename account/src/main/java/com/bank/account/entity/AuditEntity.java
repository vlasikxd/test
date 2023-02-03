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
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Entity для таблицы audit.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "account", name = "audit")
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "entity_type")
    String entityType;

    @Column(name = "operation_type")
    String operationType;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "modified_by")
    String modifiedBy;

    @Column(name = "created_at")
    OffsetDateTime createdAt;

    @Column(name = "modified_at")
    OffsetDateTime modifiedAt;

    @Column(name = "new_entity_json")
    String newEntityJson;

    @Column(name = "entity_json")
    String entityJson;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AuditEntity audit = (AuditEntity) o;
        return Objects.equals(id, audit.id) &&
                Objects.equals(entityType, audit.entityType) &&
                Objects.equals(operationType, audit.operationType) &&
                Objects.equals(createdBy, audit.createdBy) &&
                Objects.equals(modifiedBy, audit.modifiedBy) &&
                Objects.equals(createdAt, audit.createdAt) &&
                Objects.equals(modifiedAt, audit.modifiedAt) &&
                Objects.equals(newEntityJson, audit.newEntityJson) &&
                Objects.equals(entityJson, audit.entityJson);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entityType, operationType, createdBy, modifiedBy,
                createdAt, modifiedAt, newEntityJson, entityJson);
    }
}
