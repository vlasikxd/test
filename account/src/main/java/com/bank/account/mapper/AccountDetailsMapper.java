package com.bank.account.mapper;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * TODO Mapper для {@link AccountDetailsEntity} и {@link AccountDetailsDto}.
 * Mapper для {@link AccountDetailsEntity}.
 */
@Mapper(componentModel = "spring")
public interface AccountDetailsMapper {

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @return {@link AccountDetailsEntity}
     */
    @Mapping(target = "id", ignore = true)
    AccountDetailsEntity toEntity(AccountDetailsDto accountDetails);

    /**
     * @param accountDetails {@link AccountDetailsEntity}
     * @return {@link AccountDetailsDto}
     */
    AccountDetailsDto toDto(AccountDetailsEntity accountDetails);

    /**
     * @param accountDetailsDto {@link AccountDetailsDto}
     * @param accountDetails {@link AccountDetailsEntity}
     * @return {@link AccountDetailsEntity}
     */
    @Mapping(target = "id", ignore = true)
    AccountDetailsEntity mergeToEntity(AccountDetailsDto accountDetailsDto,
                                       @MappingTarget AccountDetailsEntity accountDetails);

    /**
     * @param accountDetailsList список {@link AccountDetailsEntity}
     * @return список {@link AccountDetailsDto}
     */
    List<AccountDetailsDto> toDtoList(List<AccountDetailsEntity> accountDetailsList);
}
