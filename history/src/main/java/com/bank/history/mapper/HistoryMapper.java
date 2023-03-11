package com.bank.history.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;

/**
 * Mapper для {@link HistoryEntity} и {@link HistoryDto}.
 */
@Mapper(componentModel = "spring")
public interface HistoryMapper {

    /**
     * @param history {@link HistoryDto}
     * @return {@link HistoryEntity}
     */
    @Mapping(target = "id", ignore = true)
    HistoryEntity toEntity(HistoryDto history);

    /**
     * @param history {@link HistoryEntity}
     * @return {@link HistoryDto}
     */
    HistoryDto toDto(HistoryEntity history);

    /**
     * @param historyDto {@link HistoryDto}
     * @param history    {@link HistoryEntity}
     * @return {@link HistoryEntity}
     */
    @Mapping(target = "id", ignore = true)
    HistoryEntity mergeToEntity(HistoryDto historyDto, @MappingTarget HistoryEntity history);

    /**
     * @param histories список {@link HistoryEntity}
     * @return список {@link HistoryDto}
     */
    List<HistoryDto> toDtoList(List<HistoryEntity> histories);
}
