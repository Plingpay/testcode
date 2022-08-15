package com.example.integrationbankservice.repository;

import com.example.integrationbankservice.model.entity.BankAccountEntity;
import com.example.integrationbankservice.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findByCode(String transactionId);

    Optional<TransactionEntity> findByTransactionRequestId(String transactionRequestId);
}