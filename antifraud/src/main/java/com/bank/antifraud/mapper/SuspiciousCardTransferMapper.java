package com.bank.antifraud.mapper;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.dto.transferDto.CardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для {@link SuspiciousCardTransferEntity} и {@link SuspiciousCardTransferDto}
 */
@Mapper(componentModel = "spring")
public interface SuspiciousCardTransferMapper {

    /**
     * @param suspiciousCardTransfer {@link SuspiciousCardTransferDto}
     * @return {@link SuspiciousCardTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousCardTransferEntity toEntity(SuspiciousCardTransferDto suspiciousCardTransfer);

    /**
     * @param suspiciousCardTransfer {@link SuspiciousCardTransferEntity}
     * @return {@link SuspiciousCardTransferDto}
     */
    SuspiciousCardTransferDto toDto(SuspiciousCardTransferEntity suspiciousCardTransfer);

    /**
     * @param suspiciousCardTransfer {@link SuspiciousCardTransferDto}
     * @param transfer {@link SuspiciousCardTransferEntity}
     * @return {@link SuspiciousCardTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    SuspiciousCardTransferEntity mergeToEntity(SuspiciousCardTransferDto suspiciousCardTransfer,
                                               @MappingTarget SuspiciousCardTransferEntity transfer);

    /**
     * @param suspiciousCardTransfers {@link SuspiciousCardTransferEntity}
     * @return {@link SuspiciousCardTransferDto}
     */
    List<SuspiciousCardTransferDto> toListDto(List<SuspiciousCardTransferEntity> suspiciousCardTransfers);

    default CardTransferDto toCardTransferDto(Long cardTransferId) {
        final CardTransferDto cardTransferDto = new CardTransferDto();
        cardTransferDto.setId(cardTransferId);
        return cardTransferDto;
    }

    default Long fromCardTransferDto(CardTransferDto cardTransferDto) {
        return cardTransferDto.getId();
    }
}
