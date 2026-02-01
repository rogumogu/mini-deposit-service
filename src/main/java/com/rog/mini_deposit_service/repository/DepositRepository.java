package com.rog.mini_deposit_service.repository;

import com.rog.mini_deposit_service.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, UUID> {
    Optional<Deposit> findByTransactionId(String transactionId);
    Optional<Deposit> findByIdempotencyKey(String idempotencyKey);
}
