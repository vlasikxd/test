package com.bank.transfer.mapper;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для {@link AccountTransferEntity}, {@link AccountTransferDto}
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    /**
     * @param transfer {@link AccountTransferDto}
     * @return {@link AccountTransferEntity}
    */
    @Mapping(target = "id", ignore = true)
    AccountTransferEntity toEntity(AccountTransferDto transfer);

    /**
     * @param transfer {@link AccountTransferEntity}
     * @return {@link AccountTransferDto}
     */
    AccountTransferDto toDto(AccountTransferEntity transfer);

    /**
     * TODO transfer переименуй в transferDto.
     * @param transfer {@link AccountTransferDto}
     * TODO transferEntity переименуй в transfer.
     * @param transferEntity {@link AccountTransferEntity}
     * @return {@link AccountTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    // TODO transfer переименуй в transferDto. transferEntity переименуй в transfer.
    AccountTransferEntity mergeToEntity (AccountTransferDto transfer,
                                         @MappingTarget AccountTransferEntity transferEntity);

    /**
     * @param transfers {@link List<AccountTransferEntity>}
     * @return {@link List<AccountTransferDto>}
     */
    List<AccountTransferDto> toDtoList(List<AccountTransferEntity> transfers);
}
