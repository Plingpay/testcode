package com.example.integrationbankservice.model.entity;

import com.example.integrationbankservice.model.AccountStatus;
import com.example.integrationbankservice.model.AccountType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "banking_account")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer availableBalance;

    private Integer actualBalance;

    private String accountNo;

    private String accountName;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private Integer typeAccountNo;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private BankEntity bank;
}
