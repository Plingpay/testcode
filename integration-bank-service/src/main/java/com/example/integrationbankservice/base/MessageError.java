package com.example.integrationbankservice.base;

public class MessageError {
    public static final String SUCCESS = "Transaction complete";
    public static final String TIMEOUT_ERROR = "9PAY actively returns timeout error";
    public static final String INVALID_BANK = "Invalid bank";
    public static final String BANK_ACCOUNT_NOT_FOUND = "Bank account not found";
    public static final String INSUFFICIENT_FUNDS = "Transaction amount not enough";
    public static final String TRANSACTION_TO_9AY_ERROR = "Transfer money to 9Pay failed";
    public static final String TRANSACTION_FROM_9AY_ERROR = "Transfer money from 9Pay failed";
    public static final String VALIDATE = "Invalid request parameter";
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found";
}
