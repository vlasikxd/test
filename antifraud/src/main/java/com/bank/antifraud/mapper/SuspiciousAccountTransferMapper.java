package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.dto.transferDto.AccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для {@link SuspiciousAccountTransferEntity} и {@link SuspiciousAccountTransferDto}
 */
@Mapper(componentModel = "spring")
public interface SuspiciousAccountTransferMapper {

    /**
     * @param suspiciousAccountTransfer {@link SuspiciousAccountTransferDto}
     * @return {@link SuspiciousAccountTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousAccountTransferEntity toEntity(SuspiciousAccountTransferDto suspiciousAccountTransfer);

    /**
     * @param suspiciousAccountTransfer {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    SuspiciousAccountTransferDto toDto(SuspiciousAccountTransferEntity suspiciousAccountTransfer);

    /**
     * @param suspiciousAccountTransfer {@link SuspiciousAccountTransferDto}
     * @param transfer {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousAccountTransferEntity mergeToEntity(SuspiciousAccountTransferDto suspiciousAccountTransfer,
                                                  @MappingTarget SuspiciousAccountTransferEntity transfer);

    /**
     * @param suspiciousAccountTransfers {@link SuspiciousAccountTransferEntity}
     * @return {@link SuspiciousAccountTransferDto}
     */
    List<SuspiciousAccountTransferDto> toListDto(List<SuspiciousAccountTransferEntity> suspiciousAccountTransfers);

    default AccountTransferDto toAccountTransferDto(Long accountTransferId) {
        final AccountTransferDto accountTransferDto = new AccountTransferDto();
        accountTransferDto.setId(accountTransferId);
        return accountTransferDto;
    }

    default Long fromAccountTransferDto(AccountTransferDto accountTransferDto) {
        return accountTransferDto.getId();
    }
}
