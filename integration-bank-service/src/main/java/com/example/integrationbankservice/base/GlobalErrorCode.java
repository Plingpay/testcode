package com.example.integrationbankservice.base;

public class GlobalErrorCode {
    public static final Integer SUCCESS = 200;

    public static final Integer USER_NOT_FOUND = 1001;

    public static final Integer INVALID_CREDENTIAL = 1017;
    public static final Integer TRANSACTION_NOT_FOUND = 1021;
    public static final Integer INSUFFICIENT_FUNDS = 1024;
    public static final Integer TRANSACTION_ERROR = 1026;

    public static final Integer SERVICE_NOT_ACTIVE = 1040;
    public static final Integer INVALID_BANK = 1042;
    public static final Integer BANK_ACCOUNT_NOT_FOUND = 1043;
    public static final Integer TIMEOUT = 99;
    public static final Integer VALIDATE = 100;
}
