package com.bank.transfer.repository;

import com.bank.transfer.entity.PhoneTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для {@link PhoneTransferEntity}
 */
public interface PhoneRepository extends JpaRepository<PhoneTransferEntity, Long> {
}
