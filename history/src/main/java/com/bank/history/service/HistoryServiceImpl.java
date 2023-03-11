package com.bank.history.service;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import com.bank.history.repository.HistoryRepository;

/**
 * Реализация {@link HistoryService}
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HistoryServiceImpl implements HistoryService {

    HistoryMapper mapper;
    HistoryRepository repository;

    /**
     * @param id технический идентификатор {@link HistoryEntity}.
     * @return {@link HistoryDto}
     */
    @Override
    public HistoryDto readById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> returnEntityNotFoundException("история по указанному id не найдена")));
    }

    /**
     * @param ids список технических идентификаторов {@link HistoryEntity}.
     * @return список {@link HistoryDto}
     */
    @Override
    public List<HistoryDto> readAllById(List<Long> ids) {

        final List<HistoryEntity> histories = repository.findAllById(ids);

        if (ids.size() != histories.size()) {
            throw returnEntityNotFoundException("не все запрашиваемые истории найдены");
        }

        return mapper.toDtoList(histories);
    }

    /**
     * @param historyDto {@link HistoryDto}
     * @return {@link HistoryDto}
     */
    @Override
    @Transactional
    public HistoryDto create(HistoryDto historyDto) {
        final HistoryEntity history = repository.save(
                mapper.toEntity(historyDto)
        );

        return mapper.toDto(history);
    }

    /**
     * @param id         технический идентификатор {@link HistoryEntity}.
     * @param historyDto {@link HistoryDto}
     * @return {@link HistoryDto}
     */
    @Override
    @Transactional
    public HistoryDto update(Long id, HistoryDto historyDto) {

        final HistoryEntity history = repository.findById(id)
                .orElseThrow(() -> returnEntityNotFoundException("указанная история не найдена" + id));

        final HistoryEntity updatedHistory = repository.save(
                mapper.mergeToEntity(historyDto, history)
        );

        return mapper.toDto(updatedHistory);
    }

    private EntityNotFoundException returnEntityNotFoundException(String massage) {
        return new EntityNotFoundException(massage);
    }
}
