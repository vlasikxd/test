package com.bank.publicinfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankDetailsDto implements Serializable {

    Long id;

    @NotBlank(message = "БИК должен быть заполнен")
    @Size(max = 9, min = 9, message = "БИК должен состоять из 9 цифр")
    @Pattern(regexp = "\\d{9}", message = "БИК должен состоять из цифр")
    String bik;

    @NotBlank(message = "ИНН должен быть заполнен")
    @Size(max = 10, min = 10, message = "ИНН должен состоять из 10 цифр")
    @Pattern(regexp = "\\d{10}", message = "ИНН должен состоять из цифр")
    String inn;

    @NotBlank(message = "КПП должен быть заполнен")
    @Size(max = 9, min = 9, message = "КПП должен состоять из 9 цифр")
    @Pattern(regexp = "\\d{9}", message = "КПП должен состоять из цифр")
    String kpp;

    @NotBlank(message = "Корреспондентский счёт должен быть заполнен")
    @Size(max = 9, min = 9, message = "Корреспондентский счёт должен состоять из 20 цифр")
    @Pattern(regexp = "301\\d{17}", message = "Корреспондентский счёт должен состоять из цифр и начинаться с 301")
    String corAccount;

    String city;

    @NotBlank(message = "Акционерное общество должно быть заполнено")
    @Pattern(regexp = "ПАО.+", message = "Акционерное общество должны быть - ПАО")
    String jointStockCompany;

    String name;
}
