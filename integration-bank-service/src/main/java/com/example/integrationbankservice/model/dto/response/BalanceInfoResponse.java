package com.example.integrationbankservice.model.dto.response;

import com.example.integrationbankservice.model.entity.BankAccountEntity;
import lombok.Data;

import java.util.List;

@Data
public class BalanceInfoResponse {
    private String requestTime;
    private String responseTime;
    private List<BankAccountEntity> accountEntityList;
}
