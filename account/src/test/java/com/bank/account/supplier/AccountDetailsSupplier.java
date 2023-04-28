package com.bank.account.supplier;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;

import java.math.BigDecimal;
import java.util.List;

public class AccountDetailsSupplier {

    public static AccountDetailsDto getAccountDetailsDto(Long id, Long passportId, Long accountNumber,
                                                  Long bankDetailsId, BigDecimal money,
                                                  Boolean negativeBalance, Long profileId) {
        return AccountDetailsDto.builder()
                .id(id)
                .passportId(passportId)
                .accountNumber(accountNumber)
                .bankDetailsId(bankDetailsId)
                .money(money)
                .negativeBalance(negativeBalance)
                .profileId(profileId)
                .build();
    }

    public static AccountDetailsEntity getAccountDetails(Long id, Long passportId, Long accountNumber,
                                                  Long bankDetailsId, BigDecimal money,
                                                  Boolean negativeBalance, Long profileId) {

        return new AccountDetailsEntity(id, passportId, accountNumber, bankDetailsId,
                money, negativeBalance, profileId);
    }

    public static List<AccountDetailsEntity> getAccountDetailsList(AccountDetailsEntity ... accountDetails) {
        return List.of(accountDetails);
    }

    public static  <T> T getZeroElement(List<T> result) {
        return result.get(0);
    }
}
