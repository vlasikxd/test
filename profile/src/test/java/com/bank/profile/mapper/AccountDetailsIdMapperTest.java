package com.bank.profile.mapper;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.supplier.AccountDetailsIdSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AccountDetailsIdMapperTest extends ParentTest {

    private static AccountDetailsIdMapper mapper;

    private static AccountDetailsIdEntity account;

    private static AccountDetailsIdDto accountDto;
    private static AccountDetailsIdDto accountUpdateDto;

    @BeforeAll
    static void init() {
        mapper = new AccountDetailsIdMapperImpl();

        AccountDetailsIdSupplier accountDetailsIdSupplier = new AccountDetailsIdSupplier();

        accountDto = accountDetailsIdSupplier.getDto(ONE, TWO, null);
        accountUpdateDto = accountDetailsIdSupplier.getDto(null, TWO, null);

        account = accountDetailsIdSupplier.getEntity(ONE, TWO, null);
    }

    @Test
    @DisplayName("маппинг к entity позитивный сценарий")
    void toEntityTest() {
        final AccountDetailsIdEntity result = mapper.toEntity(accountDto);

        assertAll(() -> {
            assertNull(result.getId());
            assertNull(result.getProfile());
            assertEquals(accountDto.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("маппинг к entity с параметром null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг к dto позитивный сценарий")
    void toDtoTest() {
        final AccountDetailsIdDto result = mapper.toDto(account);

        assertAll(() -> {
            assertNull(result.getProfile());
            assertEquals(account.getId(), result.getId());
            assertEquals(account.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("маппинг к dto с параметром null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("слияние в entity позитивный сценарий")
    void mergeToEntityPositiveTest() {
        final AccountDetailsIdEntity result = mapper.mergeToEntity(accountUpdateDto, account);

        assertAll(() -> {
            assertNull(result.getProfile());
            assertEquals(account.getId(), result.getId());
            assertEquals(account.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("слияние в entity, где dto равен null")
    void mergeToEntityNullTest() {
        final AccountDetailsIdEntity result = mapper.mergeToEntity(null, account);

        assertAll(() -> {
            assertNull(result.getProfile());
            assertEquals(account.getId(), result.getId());
            assertEquals(account.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("маппинг к списку дто позитивный сценарий")
    void toDtoListTest() {
        final List<AccountDetailsIdDto> accountDetailsIds = mapper.toDtoList(List.of(account));

        final AccountDetailsIdDto result = accountDetailsIds.get(0);

        assertAll(() -> {
            assertNull(result.getProfile());
            assertEquals(ONE, accountDetailsIds.size());
            assertEquals(account.getId(), result.getId());
            assertEquals(account.getAccountId(), result.getAccountId());
        });
    }

    @Test
    @DisplayName("маппинг к списку дто c параметром null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("маппинг к списку dto, где один из элементов списка равен null")
    void toListDtoElementNullTest() {
        final List<AccountDetailsIdEntity> accountDetailsIds = new ArrayList<>();
        accountDetailsIds.add(account);
        accountDetailsIds.add(null);

        final List<AccountDetailsIdDto> actually = mapper.toDtoList(accountDetailsIds);
        final var zeroIndexResult = actually.get(0);

        assertAll(() -> {
            assertNull(actually.get(1));
            assertNull(zeroIndexResult.getProfile());

            assertEquals(TWO, actually.size());
            assertEquals(account.getId(), zeroIndexResult.getId());
            assertEquals(account.getAccountId(), zeroIndexResult.getAccountId());
        });
    }
}
