package com.bank.account;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public abstract class ParentTest {

    protected static final Long ONE = 1L;
    protected static final Long TWO = 2L;
    protected static final BigDecimal BIG_DECIMAL_THREE = BigDecimal.valueOf(3L);

    public static class AccountDetailsSupplier {


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

        public static AccountDetailsEntity getZeroEntityElement(List<AccountDetailsEntity> result) {
            return result.get(0);
        }

        public static AccountDetailsDto getZeroElement(List<AccountDetailsDto> result) {
            return result.get(0);
        }
    }
}
