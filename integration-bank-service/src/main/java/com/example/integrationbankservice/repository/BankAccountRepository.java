package com.example.integrationbankservice.repository;

import com.example.integrationbankservice.model.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    Optional<BankAccountEntity> findByAccountNo(String accountNo);
    Optional<BankAccountEntity> findByAccountNoAndTypeAccountNo(String accountNo, Integer typeAccountNo);
    Optional<BankAccountEntity> findByAccountNoAndTypeAccountNoAndAccountName(String accountNo, Integer typeAccountNo, String accountName);
}