package com.bank.publicinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankDetailsDto implements Serializable {

    Long id;

    @NotBlank(message = "БИК должен быть заполнен")
    @Pattern(regexp = "\\d{9}", message = "БИК должен состоять из цифр")
    String bik;

    @NotBlank(message = "ИНН должен быть заполнен")
    @Pattern(regexp = "\\d{10}", message = "ИНН должен состоять из цифр")
    String inn;

    @NotBlank(message = "КПП должен быть заполнен")
    @Pattern(regexp = "\\d{9}", message = "КПП должен состоять из цифр")
    String kpp;

    @NotBlank(message = "Корреспондентский счёт должен быть заполнен")
    @Pattern(regexp = "301\\d{17}", message = "Корреспондентский счёт должен состоять из цифр")
    String corAccount;

    String city;

    @NotBlank(message = "Акционерное общество должно быть заполненно")
    @Pattern(regexp = "ПАО.+", message = "Акционерное общество должны быть - \"ПАО\"")
    String jointStockCompany;

    String name;
}
