package com.bank.publicinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankDetailsDto {
    Long id;
    Long bik;
    Long inn;
    Long kpp;
    Integer corAccount;
    String city;
    String jointStockCompany;
    String name;
}
