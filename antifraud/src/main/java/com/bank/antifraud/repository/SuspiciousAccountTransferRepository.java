package com.bank.antifraud.repository;

import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для {@link SuspiciousAccountTransferEntity}
 */
@Repository
public interface SuspiciousAccountTransferRepository extends JpaRepository<SuspiciousAccountTransferEntity, Long> {
}
