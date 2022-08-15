package com.example.integrationbankservice.model.dto.response;

import com.example.integrationbankservice.model.AccountType;
import lombok.Data;

@Data
public class VerifyResponse{
    private String requestId;
    private String partnerId;
    private String bankNo;
    private String accountNo;
    private AccountType accountType;
    private String accountName;
    private String signature;
}
