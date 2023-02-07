package com.bank.antifraud.repository;

import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для {@link SuspiciousPhoneTransferEntity}
 */
@Repository
public interface SuspiciousPhoneTransferRepository extends JpaRepository<SuspiciousPhoneTransferEntity, Long> {
}
