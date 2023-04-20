package com.bank.publicinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuditDto implements Serializable {
    Long id;
    String entityType;
    String operationType;
    String cratedBy;
    String modifiedBy;
    Timestamp createdAt;
    Timestamp modifiedAt;
    String newEntityJson;
    String entityJson;
}
