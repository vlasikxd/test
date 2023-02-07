package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для {@link SuspiciousPhoneTransferEntity} и {@link SuspiciousPhoneTransferDto}
 */
@Mapper(componentModel = "spring")
public interface SuspiciousPhoneTransferMapper {

    /**
     * @param suspiciousPhoneTransfer {@link SuspiciousPhoneTransferDto}
     * @return {@link SuspiciousPhoneTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousPhoneTransferEntity toEntity(SuspiciousPhoneTransferDto suspiciousPhoneTransfer);

    /**
     * @param suspiciousPhoneTransfer {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    SuspiciousPhoneTransferDto toDto(SuspiciousPhoneTransferEntity suspiciousPhoneTransfer);

    /**
     * @param suspiciousPhoneTransfer {@link SuspiciousPhoneTransferDto}
     * @param transfer {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousPhoneTransferEntity mergeToEntity(SuspiciousPhoneTransferDto suspiciousPhoneTransfer,
                                                @MappingTarget SuspiciousPhoneTransferEntity transfer);

    /**
     * @param suspiciousPhoneTransfers {@link SuspiciousPhoneTransferEntity}
     * @return {@link SuspiciousPhoneTransferDto}
     */
    List<SuspiciousPhoneTransferDto> toListDto(List<SuspiciousPhoneTransferEntity> suspiciousPhoneTransfers);
}
