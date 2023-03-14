package com.bank.publicinfo.service;

import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.entity.BranchEntity;

import java.util.List;

/**
 * Service для {@link BranchDto}
 */
public interface BranchService {

    /**
     * @param id технический идентификатор {@link BranchEntity}.
     * @return {@link BranchDto}
     */
    BranchDto read(Long id);

    /**
     * @param ids список технических индентификаторов {@link BranchEntity}
     * @return список {@link BranchDto}
     */
    List<BranchDto> readAll(List<Long> ids);

    /**
     * @param branch {@link BranchDto}
     * @return {@link BranchDto}
     */
    BranchDto save(BranchDto branch);

    /**
     * @param id     технический идентификатор {@link BranchEntity}
     * @param branch {@link BranchDto}
     * @return {@link BranchDto}
     */
    BranchDto update(Long id, BranchDto branch);
}
