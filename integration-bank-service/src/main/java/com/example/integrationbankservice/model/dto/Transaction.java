package com.example.integrationbankservice.model.dto;

import com.example.integrationbankservice.model.TransactionStatus;
import com.example.integrationbankservice.model.TransactionType;
import com.example.integrationbankservice.model.entity.BankAccountEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private Long id;

    private String transactionRequestId;

    private String code;

    private Integer requestAmount;

    private Integer transferAmount;

    private Integer fee;

    private String content;

    private TransactionType type;

    private TransactionStatus status;

    private Date createdAt;

    private BankAccountEntity account;
}
