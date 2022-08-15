package com.example.integrationbankservice.repository;

import com.example.integrationbankservice.model.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<BankEntity, Long> {
    Optional<BankEntity> findByBankNo(String bankNo);
}
