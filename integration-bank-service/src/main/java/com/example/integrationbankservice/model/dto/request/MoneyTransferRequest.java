package com.example.integrationbankservice.model.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MoneyTransferRequest {

    private String fromBankNo;
    private String fromAccountNo;
    private Integer fromAccountType;
    private String fromAccountName;

    private String toBankNo;
    private String toAccountNo;
    private Integer toAccountType;
    private String toAccountName;

    private Integer amount;
    private String content;
}
