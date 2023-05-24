package com.bank.account.dto.profileDto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDto {

    Long id;
    Long phoneNumber;
    String email;
    String nameOnCard;
    Long inn;
    Long snils;
    PassportDto passport;
    ActualRegistrationDto actualRegistration;
}
