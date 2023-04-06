package com.bank.account.mapper;

import com.bank.account.ParentTest;
import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountDetailsMapperTest extends ParentTest {

    private AccountDetailsMapper mapper;

    private AccountDetailsEntity accountDetails;

    private AccountDetailsDto accountDetailsDto;
    private AccountDetailsDto updateAccountDetails;

    private List<AccountDetailsEntity> accountDetailsList;

    @BeforeEach
    void prepare() {
        mapper = new AccountDetailsMapperImpl();
        accountDetailsDto = getAccountDetailsDto(ONE, ONE, ONE, ONE, BIG_DECIMAL_THREE, Boolean.TRUE, ONE);
        updateAccountDetails = getAccountDetailsDto(null, TWO, TWO, TWO, BIG_DECIMAL_THREE, Boolean.FALSE, TWO);

        accountDetails = getAccountDetails(ONE, ONE, ONE, ONE, BIG_DECIMAL_THREE, Boolean.TRUE, ONE);
        AccountDetailsEntity accountDetailsUpdate = getAccountDetails(ONE, TWO, TWO, TWO, BIG_DECIMAL_THREE, Boolean.FALSE, TWO);

        accountDetailsList = getAccountDetailsList(accountDetails, accountDetailsUpdate);
    }

    @Test
    @DisplayName("маппинг в entity позитивный сценарий")
    void toEntityTest() {
        final AccountDetailsEntity result = mapper.toEntity(accountDetailsDto);

        assertAll(() -> {
            assertNotEquals(accountDetailsDto.getId(), result.getId());
            assertEquals(accountDetails.getMoney(), result.getMoney());
            assertEquals(accountDetails.getProfileId(), result.getProfileId());
            assertEquals(accountDetails.getPassportId(), result.getPassportId());
            assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
            assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
            assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        });
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг в dto позитивный сценарий")
    void toDtoTest() {
        final AccountDetailsDto result = mapper.toDto(accountDetails);

        assertAll(() -> {
            assertEquals(accountDetails.getId(), result.getId());
            assertEquals(accountDetails.getMoney(), result.getMoney());
            assertEquals(accountDetails.getProfileId(), result.getProfileId());
            assertEquals(accountDetails.getPassportId(), result.getPassportId());
            assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
            assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
            assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        });
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("маппинг в entity позитивный сценарий")
    void mergeToEntityTest() {
        final AccountDetailsEntity result = mapper.mergeToEntity(updateAccountDetails, accountDetails);

        assertAll(() -> {
            assertNotEquals(updateAccountDetails.getId(), result.getId());
            assertEquals(accountDetails.getMoney(), result.getMoney());
            assertEquals(accountDetails.getProfileId(), result.getProfileId());
            assertEquals(accountDetails.getPassportId(), result.getPassportId());
            assertEquals(accountDetails.getAccountNumber(), result.getAccountNumber());
            assertEquals(accountDetails.getBankDetailsId(), result.getBankDetailsId());
            assertEquals(accountDetails.getNegativeBalance(), result.getNegativeBalance());
        });
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final AccountDetailsEntity result = mapper.mergeToEntity(null, accountDetails);

        assertAll(() -> {
            assertEquals(ONE, result.getId());
            assertEquals(ONE, result.getProfileId());
            assertEquals(ONE, result.getPassportId());
            assertEquals(ONE, result.getAccountNumber());
            assertEquals(ONE, result.getBankDetailsId());
            assertEquals(BIG_DECIMAL_THREE, result.getMoney());
            assertEquals(Boolean.TRUE, result.getNegativeBalance());
        });
    }

    @Test
    @DisplayName("маппинг списка entity в список dto позитивный сценарий")
    void toDtoListTest() {
        final List<AccountDetailsDto> result = mapper.toDtoList(accountDetailsList);
        final AccountDetailsDto firstAccountDetailsDto = getZeroElement(result);
        final AccountDetailsEntity firstAccountDetailsEntity = getZeroElement(accountDetailsList);
        assertAll(() -> {
            assertEquals(accountDetailsList.size(), result.size());
            assertEquals(firstAccountDetailsEntity.getId(), firstAccountDetailsDto.getId());
            assertEquals(firstAccountDetailsEntity.getMoney(), firstAccountDetailsDto.getMoney());
            assertEquals(firstAccountDetailsEntity.getProfileId(), firstAccountDetailsDto.getProfileId());
            assertEquals(firstAccountDetailsEntity.getPassportId(), firstAccountDetailsDto.getPassportId());
            assertEquals(firstAccountDetailsEntity.getAccountNumber(), firstAccountDetailsDto.getAccountNumber());
            assertEquals(firstAccountDetailsEntity.getBankDetailsId(), firstAccountDetailsDto.getBankDetailsId());
            assertEquals(firstAccountDetailsEntity.getNegativeBalance(), firstAccountDetailsDto.getNegativeBalance());
        });
    }

    @Test
    @DisplayName("маппинг списка entity в список dto, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("маппинг списка entity в список dto, один элемент списка null")
    void toDtoListOneElementNullTest() {
        List<AccountDetailsEntity> severalAccountDetails = new ArrayList<>();
        severalAccountDetails.add(accountDetails);
        severalAccountDetails.add(null);

        final List<AccountDetailsDto> result = mapper.toDtoList(severalAccountDetails);
        final AccountDetailsDto firstAccountDetailsDto = getZeroElement(result);
        final AccountDetailsEntity firstAccountDetailsEntity = getZeroElement(accountDetailsList);

        assertAll(() -> {
            assertNull(result.get(1));
            assertEquals(accountDetailsList.size(), result.size());
            assertEquals(firstAccountDetailsEntity.getId(), firstAccountDetailsDto.getId());
            assertEquals(firstAccountDetailsEntity.getMoney(), firstAccountDetailsDto.getMoney());
            assertEquals(firstAccountDetailsEntity.getProfileId(), firstAccountDetailsDto.getProfileId());
            assertEquals(firstAccountDetailsEntity.getPassportId(), firstAccountDetailsDto.getPassportId());
            assertEquals(firstAccountDetailsEntity.getAccountNumber(), firstAccountDetailsDto.getAccountNumber());
            assertEquals(firstAccountDetailsEntity.getBankDetailsId(), firstAccountDetailsDto.getBankDetailsId());
            assertEquals(firstAccountDetailsEntity.getNegativeBalance(), firstAccountDetailsDto.getNegativeBalance());
        });
    }
}
