package com.example.integrationbankservice.model.dto;

import com.example.integrationbankservice.model.AccountStatus;
import com.example.integrationbankservice.model.AccountType;
import com.example.integrationbankservice.model.entity.BankEntity;
import com.example.integrationbankservice.model.entity.UserEntity;
import lombok.Data;

@Data
public class BankAccount {
    private Long id;

    private Integer availableBalance;

    private Integer actualBalance;

    private String accountNo;

    private String accountName;

    private AccountStatus status;

    private AccountType type;

    private Integer typeAccountNo;

    private UserEntity user;

    private BankEntity bank;
}
