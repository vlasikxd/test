package com.bank.profile.mapper;

import com.bank.profile.ParentTest;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.supplier.ProfileSupplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProfileMapperTest extends ParentTest {

    private static ProfileMapper mapper;
    private static ProfileEntity profile;
    private static ProfileDto profileDto;
    private static ProfileDto updateProfileDto;

    @BeforeAll
    static void init() {
        mapper = new ProfileMapperImpl();

        ProfileSupplier profileSupplier = new ProfileSupplier();

        profileDto = profileSupplier.getDto(ONE, ONE, WHITESPACE, WHITESPACE);

        updateProfileDto = profileSupplier.getDto(null, ONE, WHITESPACE, WHITESPACE);

        profile = profileSupplier.getEntity(ONE, ONE, WHITESPACE, WHITESPACE);
    }

    @Test
    @DisplayName("маппинг к entity")
    void toEntityTest() {
        final ProfileEntity result = mapper.toEntity(profileDto);

        assertAll(
                () -> {
                    assertNull(result.getId());
                    assertNull(result.getPassport());
                    assertNull(result.getActualRegistration());
                    assertEquals(profileDto.getInn(), result.getInn());
                    assertEquals(profileDto.getEmail(), result.getEmail());
                    assertEquals(profileDto.getSnils(), result.getSnils());
                    assertEquals(profileDto.getNameOnCard(), result.getNameOnCard());
                    assertEquals(profileDto.getPhoneNumber(), result.getPhoneNumber());
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
        final ProfileDto result = mapper.toDto(profile);

        assertAll(
                () -> {
                    assertNull(result.getPassport());
                    assertNull(result.getActualRegistration());
                    assertEquals(profile.getId(), result.getId());
                    assertEquals(profile.getInn(), result.getInn());
                    assertEquals(profile.getEmail(), result.getEmail());
                    assertEquals(profile.getSnils(), result.getSnils());
                    assertEquals(profile.getNameOnCard(), result.getNameOnCard());
                    assertEquals(profile.getPhoneNumber(), result.getPhoneNumber());
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
        final ProfileEntity result = mapper.mergeToEntity(updateProfileDto, profile);

        assertAll(
                () -> {
                    assertNull(result.getPassport());
                    assertNull(result.getActualRegistration());
                    assertEquals(profile.getId(), result.getId());
                    assertEquals(profile.getInn(), result.getInn());
                    assertEquals(profile.getEmail(), result.getEmail());
                    assertEquals(profile.getSnils(), result.getSnils());
                    assertEquals(profile.getNameOnCard(), result.getNameOnCard());
                    assertEquals(profile.getPhoneNumber(), result.getPhoneNumber());
                }
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        final ProfileEntity result = mapper.mergeToEntity(null, profile);

        assertAll(
                () -> {
                    assertNull(result.getPassport());
                    assertNull(result.getActualRegistration());
                    assertEquals(profile.getId(), result.getId());
                    assertEquals(profile.getInn(), result.getInn());
                    assertEquals(profile.getEmail(), result.getEmail());
                    assertEquals(profile.getSnils(), result.getSnils());
                    assertEquals(profile.getNameOnCard(), result.getNameOnCard());
                    assertEquals(profile.getPhoneNumber(), result.getPhoneNumber());
                }
        );
    }

    @Test
    @DisplayName("маппинг к списку dto")
    void toDtoListTest() {
        final List<ProfileDto> profiles = mapper.toDtoList(List.of(profile));

        final ProfileDto result = profiles.get(0);

        assertAll(
                () -> {
                    assertNull(result.getPassport());
                    assertNull(result.getActualRegistration());
                    assertEquals(ONE, profiles.size());
                    assertEquals(profile.getId(), result.getId());
                    assertEquals(profile.getInn(), result.getInn());
                    assertEquals(profile.getEmail(), result.getEmail());
                    assertEquals(profile.getSnils(), result.getSnils());
                    assertEquals(profile.getNameOnCard(), result.getNameOnCard());
                    assertEquals(profile.getPhoneNumber(), result.getPhoneNumber());
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
        final List<ProfileEntity> profiles = new ArrayList<>();
        profiles.add(profile);
        profiles.add(null);

        final List<ProfileDto> actually = mapper.toDtoList(profiles);
        final var zeroIndexResult = actually.get(0);

        assertAll(
                () -> {
                    assertNull(actually.get(1));
                    assertNull(zeroIndexResult.getPassport());
                    assertNull(zeroIndexResult.getActualRegistration());
                    assertEquals(TWO, actually.size());
                    assertEquals(profile.getId(), zeroIndexResult.getId());
                    assertEquals(profile.getInn(), zeroIndexResult.getInn());
                    assertEquals(profile.getEmail(), zeroIndexResult.getEmail());
                    assertEquals(profile.getSnils(), zeroIndexResult.getSnils());
                    assertEquals(profile.getNameOnCard(), zeroIndexResult.getNameOnCard());
                    assertEquals(profile.getPhoneNumber(), zeroIndexResult.getPhoneNumber());
                }
        );
    }
}
