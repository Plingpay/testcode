package com.example.integrationbankservice.model.dto.response;

import com.example.integrationbankservice.model.TransactionStatus;
import com.example.integrationbankservice.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private String transactionRequestId;
    private String requestId;
    private String partnerId;
    private String transactionId;
    private String bankNo;
    private String accountNo;
    private Integer accountType;
    private String accountName;
    private Integer requestAmount;
    private Integer transferAmount;
    private String content;
    private TransactionType type;
    private TransactionStatus status;
    private String createdAt;
    private String signature;
}
