package com.bank.transfer.mapper;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для {@link PhoneTransferEntity}
 */
@Mapper(componentModel = "spring")
public interface PhoneMapper {

    /**
     * @param dto {@link PhoneTransferDto}
     * @return {@link PhoneTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    PhoneTransferEntity toEntity(PhoneTransferDto dto);

    /**
     * @param transfer {@link PhoneTransferEntity}
     * @return {@link PhoneTransferDto}
     */
    PhoneTransferDto toDto(PhoneTransferEntity transfer);

    /**
     * TODO transfer переименуй в transferDto.
     * @param transfer {@link PhoneTransferDto}
     * TODO transferEntity переименуй в transfer.
     * @param transferEntity {@link PhoneTransferEntity}
     * @return {@link PhoneTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    // TODO transfer переименуй в transferDto. transferEntity переименуй в transfer.
    PhoneTransferEntity mergeToEntity (PhoneTransferDto transfer,
                                      @MappingTarget PhoneTransferEntity transferEntity);

    /**
     * @param transfers {@link List<PhoneTransferEntity>}
     * @return {@link List<PhoneTransferDto>}
     */
    List<PhoneTransferDto> toDtoList(List<PhoneTransferEntity> transfers);
}
