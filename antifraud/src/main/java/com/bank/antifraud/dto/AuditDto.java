package com.bank.antifraud.dto;

import com.bank.antifraud.entity.AuditEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO для сущности {@link AuditEntity}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditDto implements Serializable {

    Long id;

    @NotNull
    @Size(max = 40)
    String entityType;

    @NotNull
    @Size(max = 255)
    String operationType;

    @NotNull
    @Size(max = 255)
    String createdBy;

    @Size(max = 255)
    String modifiedBy;

    @NotNull
    Timestamp createdAt;

    Timestamp modifiedAt;

    String newEntityJson;

    @NotNull
    private String entityJson;
}
