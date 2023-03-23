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
    private static AccountDetailsIdEntity accountDetailsId;
    private static AccountDetailsIdDto accountDetailsIdDto;
    private static AccountDetailsIdDto updateAccountDto;

    @BeforeAll
    static void init() {
        mapper = new AccountDetailsIdMapperImpl();

        AccountDetailsIdSupplier accountDetailsIdSupplier = new AccountDetailsIdSupplier();

        accountDetailsIdDto = accountDetailsIdSupplier.getDto(ONE, TWO, null);

        updateAccountDto = accountDetailsIdSupplier.getDto(null, TWO, null);

        accountDetailsId = accountDetailsIdSupplier.getEntity(ONE, TWO, null);
    }

    @Test
    @DisplayName("маппинг к entity")
    void toEntityTest() {
        final AccountDetailsIdEntity result = mapper.toEntity(accountDetailsIdDto);

        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertNull(result.getProfile());
                    assertEquals(accountDetailsIdDto.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("маппинг к entity, на вход подан null")
    void toEntityNullTest() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    @DisplayName("маппинг к dto")
    void toDtoTest() {
        final AccountDetailsIdDto result = mapper.toDto(accountDetailsId);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(accountDetailsId.getId(), result.getId());
                    assertEquals(accountDetailsId.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("маппинг к dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(mapper.toDto(null));
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        final AccountDetailsIdEntity result = mapper.mergeToEntity(updateAccountDto, accountDetailsId);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(accountDetailsId.getId(), result.getId());
                    assertEquals(accountDetailsId.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final AccountDetailsIdEntity result = mapper.mergeToEntity(null, accountDetailsId);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(accountDetailsId.getId(), result.getId());
                    assertEquals(accountDetailsId.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("маппинг к списку dto")
    void toDtoListTest() {
        final List<AccountDetailsIdDto> accountDetailsIds = mapper.toDtoList(List.of(accountDetailsId));

        final AccountDetailsIdDto result = accountDetailsIds.get(0);

        assertAll(
                () -> {
                    assertNull(result.getProfile());
                    assertEquals(ONE, accountDetailsIds.size());
                    assertEquals(accountDetailsId.getId(), result.getId());
                    assertEquals(accountDetailsId.getAccountId(), result.getAccountId());
                }
        );
    }

    @Test
    @DisplayName("маппинг к списку dto, на вход подан null")
    void toDtoListNullTest() {
        assertNull(mapper.toDtoList(null));
    }

    @Test
    @DisplayName("маппинг к списку dto, один из элементов списка null")
    void toListDtoElementNullTest() {
        final List<AccountDetailsIdEntity> accountDetailsIds = new ArrayList<>();
        accountDetailsIds.add(accountDetailsId);
        accountDetailsIds.add(null);

        final List<AccountDetailsIdDto> actually = mapper.toDtoList(accountDetailsIds);
        final var zeroIndexResult = actually.get(0);

        assertAll(
                () -> {
                    assertNull(actually.get(1));
                    assertNull(zeroIndexResult.getProfile());
                    assertEquals(TWO, actually.size());
                    assertEquals(accountDetailsId.getId(), zeroIndexResult.getId());
                    assertEquals(accountDetailsId.getAccountId(), zeroIndexResult.getAccountId());
                }
        );
    }
}
