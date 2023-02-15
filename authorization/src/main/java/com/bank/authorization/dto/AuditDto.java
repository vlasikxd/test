package com.bank.authorization.dto;

import com.bank.authorization.entity.AuditEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ДТО для {@link AuditEntity}.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditDto implements Serializable {

    Long id;

    @NotNull
    String entityType;

    @NotNull
    String operationType;

    @NotNull
    String createdBy;

    String modifiedBy;

    @NotNull
    Timestamp createdAt;

    Timestamp modifiedAt;

    String newEntityJson;

    @NotNull
    String entityJson;
}
