package com.bank.account.dto;

import com.bank.account.entity.AuditEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * Dto для {@link AuditEntity}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditDto {
    Long id;
    String entityType;
    String operationType;
    String createdBy;
    String modifiedBy;
    String newEntityJson;
    String entityJson;
}
