package com.bank.transfer.service.impl;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.exception.EntityNotFoundReturner;
import com.bank.transfer.mapper.PhoneMapper;
import com.bank.transfer.repository.PhoneRepository;
import com.bank.transfer.service.PhoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO Реализация {@link PhoneService}.
 * Реализация сервис слоя для {@link PhoneTransferDto}, {@link PhoneTransferEntity}
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository repository;
    private final PhoneMapper mapper;
    private final EntityNotFoundReturner notFoundReturner;

    /**
     * @param transfer {@link PhoneTransferDto}
     * @return  {@link PhoneTransferDto}
     */
    @Override
    @Transactional
    public PhoneTransferDto create(PhoneTransferDto transfer) {
        // TODO entity переименуй в phoneTransfer.
        final PhoneTransferEntity entity = repository.save(mapper.toEntity(transfer));
        return mapper.toDto(entity);
    }

    /**
     * @param transfer {@link PhoneTransferDto}
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return  {@link PhoneTransferDto}
     */
    @Override
    @Transactional
    // TODO transfer переименуй в transferDto.
    public PhoneTransferDto update(PhoneTransferDto transfer, Long id) {
        final PhoneTransferEntity phoneTransfer = repository.findById(id).
                orElseThrow(() -> {
                    // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
                    throw notFoundReturner.getException("Запись с указанным id не найдена");
                });
        // TODO transferEntity переименуй в transfer. и смысл save(mapper.mergeToEntity(transfer, phoneTransfer)); в этом переносе?
        final PhoneTransferEntity transferEntity = repository.
                save(mapper.mergeToEntity(transfer, phoneTransfer));
        return mapper.toDto(transferEntity);
    }

    /**
     * @param id технический идентификатор {@link PhoneTransferEntity}
     * @return  {@link PhoneTransferDto}
     */
    @Override
    public PhoneTransferDto read(Long id) {
        final PhoneTransferEntity phoneTransfer = repository.findById(id).
                orElseThrow(() -> {
                    // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
                    throw notFoundReturner.getException("Запись с указанным id не найдена");
                });
        return mapper.toDto(phoneTransfer);
    }

    /**
     * @param ids лист технических идентификаторов {@link PhoneTransferEntity}
     * @return  {@link List<PhoneTransferDto>}
     */
    @Override
    public List<PhoneTransferDto> readAll(List<Long> ids) {
        final List<PhoneTransferDto> transfers = mapper.toDtoList(repository.findAllById(ids));
        // TODO вот эта проверка(if) дублируется во всех реализациях, сделай отдельный класс и переиспользуй, как с
        //  notFoundReturner.getException
        if (ids.size() != transfers.size()) {
            // TODO в сообщении явно пиши, какая сущность не найдена, а то в логах не понятно.
            throw notFoundReturner.getException("Одна или более записей из списка id не найдены");
        }
        return transfers;
    }
}
