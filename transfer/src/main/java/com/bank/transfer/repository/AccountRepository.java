package com.bank.transfer.repository;

import com.bank.transfer.entity.AccountTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для {@link AccountTransferEntity}
 */
public interface AccountRepository extends JpaRepository<AccountTransferEntity, Long> {
}
