package com.example.integrationbankservice.model.dto.request;

import lombok.Data;

@Data
public class AccountVerifyRequest {
    private String bankNo;
    private String accountNo;
    private Integer accountType;
}
