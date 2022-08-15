package com.example.integrationbankservice.model.entity;

import com.example.integrationbankservice.model.TransactionStatus;
import com.example.integrationbankservice.model.TransactionType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionRequestId;

    private String code;

    private Integer requestAmount;

    private Integer transferAmount;

    private Integer fee;

    private String content;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banking_account_id", referencedColumnName = "id")
    private BankAccountEntity account;

    public TransactionEntity() {

    }

    public TransactionEntity(String transactionRequestId, String transactionId, Integer requestAmount, Integer transferAmount, Integer fee, String content, TransactionType type, TransactionStatus status,Date createdAt,BankAccountEntity account) {
        this.transactionRequestId = transactionRequestId;
        this.code = transactionId;
        this.requestAmount = requestAmount;
        this.transferAmount = transferAmount;
        this.fee = fee;
        this.content = content;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.account = account;
    }
}