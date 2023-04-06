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
    protected static final String BAD_REQUEST_BODY_INCORRECT_ID = "Некорректно указан id";
    protected static final String BAD_REQUEST_BODY_INVALID_JSON = "Невалидный JSON";
    protected static final String BAD_REQUEST_BODY_INVALID_ENTITY = "Передана невалидная сущность";
    protected static final String TEST_STRING = "Test";
    protected static final String NOT_FOUND_BODY = "Одного или нескольких id из списка не найдено";

    protected static AccountDetailsDto getAccountDetailsDto(Long id, Long passportId, Long accountNumber,
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

    protected static AccountDetailsEntity getAccountDetails(Long id, Long passportId, Long accountNumber,
                                                            Long bankDetailsId, BigDecimal money,
                                                            Boolean negativeBalance, Long profileId) {
        return new AccountDetailsEntity(id, passportId, accountNumber, bankDetailsId,
                money, negativeBalance, profileId);
    }

    protected static List<AccountDetailsEntity> getAccountDetailsList(AccountDetailsEntity ... accountDetails) {
        return List.of(accountDetails);
    }

    protected <T> T getZeroElement(List<T> result) {
        return result.get(0);
    }
    protected static String getMissingServletRequestParameterExceptionMessage(String parameter) {
        return String.format("Необходимая часть запроса '%s' не существует", parameter);
    }
}
