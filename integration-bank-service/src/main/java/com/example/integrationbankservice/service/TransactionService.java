package com.example.integrationbankservice.service;

import com.example.integrationbankservice.base.MessageError;
import com.example.integrationbankservice.base.GlobalErrorCode;
import com.example.integrationbankservice.model.AccountStatus;
import com.example.integrationbankservice.model.TransactionStatus;
import com.example.integrationbankservice.model.TransactionType;
import com.example.integrationbankservice.model.dto.Bank;
import com.example.integrationbankservice.model.dto.request.TransactionIdRequest;
import com.example.integrationbankservice.model.dto.request.TransactionRequestId;
import com.example.integrationbankservice.model.dto.response.TransactionResponse;
import com.example.integrationbankservice.model.dto.request.MoneyTransferRequest;
import com.example.integrationbankservice.model.dto.response.CommonResponse;
import com.example.integrationbankservice.model.dto.response.ErrorResponse;
import com.example.integrationbankservice.model.entity.BankAccountEntity;
import com.example.integrationbankservice.model.entity.BankEntity;
import com.example.integrationbankservice.model.entity.TransactionEntity;
import com.example.integrationbankservice.repository.BankAccountRepository;
import com.example.integrationbankservice.repository.BankRepository;
import com.example.integrationbankservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService extends BaseService{

    private final AccountService accountService;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;
    private final BankRepository bankRepository;

    public CommonResponse moneyTransfer(MoneyTransferRequest request) throws InterruptedException {
        if(request.getFromBankNo().isEmpty() || request.getFromAccountNo().isEmpty() || request.getFromAccountType() == null
                || (request.getFromAccountType() != 0 && request.getFromAccountType() != 1)
                || request.getFromAccountName().isEmpty()
                ||request.getToBankNo().isEmpty() || request.getToAccountNo().isEmpty() || request.getToAccountType() == null
                || (request.getToAccountType() != 0 && request.getToAccountType() != 1)
                || request.getToAccountName().isEmpty()

                || request.getAmount() == null || request.getAmount() < 0 || request.getContent().isEmpty()){
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.VALIDATE,MessageError.VALIDATE));
        }

        //Random time connection
        Long rt = randomTime();
        Thread.sleep(rt);

        //Fake 9pay
        TransactionResponse transactionResponse = new TransactionResponse();

        //partner -> 9pay
        if (rt <= 1900) {

            //get random partner id
            String partnerId = getSaltString(20);

            //set request id following above partner id
            String requestId = partnerId + "9P" + new SimpleDateFormat("yyyyMMDD").format(new Date()) + getSaltString(20);

            rt = randomTime();
            Thread.sleep(rt);

            //9pay -> partner
            if (rt <= 1900) {

                //set partner signature structure
                String partnerSignatureRequest = String.format("%s|%s|%s|%s|%d|%s|%s|%s",
                        requestId,
                        partnerId,
                        request.getFromBankNo(),
                        request.getFromAccountNo(),
                        request.getFromAccountType(),
                        request.getFromAccountName(),
                        request.getAmount().toString(),
                        request.getContent()
                );

                //sign the transmitted data using RSA algorithm, then send it in base64 encoding

                rt = randomTime();
                Thread.sleep(rt);

                //partner -> 9pay
                if (rt <= 1900) {
                    String transactionId = getSaltString(20);
                    transactionResponse.setType(TransactionType.TRANSFER_BANK);

                    transactionResponse.setRequestId(requestId);
                    transactionResponse.setPartnerId(partnerId);
                    transactionResponse.setTransactionId(transactionId);
                    transactionResponse.setBankNo(request.getFromBankNo());
                    transactionResponse.setAccountNo(request.getFromAccountNo());
                    transactionResponse.setAccountType(request.getFromAccountType());
                    transactionResponse.setAccountName(request.getFromAccountName());
                    transactionResponse.setRequestAmount(request.getAmount());
                    transactionResponse.setTransferAmount(0);
                    transactionResponse.setContent(request.getContent());
                    transactionResponse.setStatus(TransactionStatus.PENDING);
                    transactionResponse.setCreatedAt(new SimpleDateFormat("yyyy-MM-DD hh:mm:ss").format(new Date()));
                    transactionResponse.setSignature(String.format("%s|%s|%s|%s|%s|%d|%s|%s|%d|%s|%s",
                            requestId,
                            partnerId,
                            transactionId,
                            request.getFromBankNo(),
                            request.getFromAccountNo(),
                            request.getFromAccountType(),
                            request.getFromAccountName(),
                            request.getAmount().toString(),
                            0,
                            AccountStatus.PENDING,
                            transactionResponse.getCreatedAt()
                    ));

                    //Check bank exists
                    Optional<BankEntity> fromBank = bankRepository.findByBankNo(request.getFromBankNo());
                    Optional<BankEntity> toBank = bankRepository.findByBankNo(request.getFromBankNo());
                    if(!fromBank.isPresent() || !toBank.isPresent()){
                        transactionResponse.setStatus(TransactionStatus.FAIL);
                        return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.INVALID_BANK,MessageError.INVALID_BANK), transactionResponse);
                    }

                    //Check exists From Account
                    Optional<BankAccountEntity> bankAccountA = bankAccountRepository.findByAccountNoAndTypeAccountNoAndAccountName(request.getFromAccountNo(), request.getFromAccountType(), request.getFromAccountName());
                    if(!bankAccountA.isPresent()){
                        transactionResponse.setStatus(TransactionStatus.FAIL);
                        return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.BANK_ACCOUNT_NOT_FOUND, MessageError.BANK_ACCOUNT_NOT_FOUND), transactionResponse);
                    }else{
                        if (bankAccountA.get().getActualBalance() < 0 || bankAccountA.get().getActualBalance().compareTo(request.getAmount()) < 0) {
                            transactionResponse.setStatus(TransactionStatus.FAIL);
                            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.INSUFFICIENT_FUNDS, MessageError.INSUFFICIENT_FUNDS), transactionResponse);
                        }
                    }

                    Optional<BankAccountEntity> bankAccountB = bankAccountRepository.findByAccountNoAndTypeAccountNoAndAccountName(request.getToAccountNo(), request.getToAccountType(), request.getToAccountName());
                    if(!bankAccountB.isPresent()){
                        transactionResponse.setStatus(TransactionStatus.FAIL);
                        return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.BANK_ACCOUNT_NOT_FOUND, MessageError.BANK_ACCOUNT_NOT_FOUND), transactionResponse);
                    }

                    //Partner transfer money from bank account A to 9Pay
                    Integer ninePayBankAccount = 0;
                    Integer requestAmount;
                    Integer feeAmount = 0;

                    rt = randomTime();
                    Thread.sleep(rt);

                    //transaction bank account A -> 9pay
                    if (rt <= 1900) {
                        bankAccountA.get().setAvailableBalance(bankAccountA.get().getAvailableBalance() - request.getAmount());
                        bankAccountA.get().setActualBalance(bankAccountA.get().getActualBalance() - request.getAmount());
                        ninePayBankAccount += request.getAmount();
                        requestAmount = request.getAmount() + feeAmount;
                        transactionResponse.setRequestAmount(requestAmount);
                    }else{
                        transactionResponse.setRequestAmount(request.getAmount());
                        transactionResponse.setStatus(TransactionStatus.REFUND);
                        internalFundTransfer(bankAccountA.get(), bankAccountB.get(), transactionResponse, feeAmount);
                        return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TRANSACTION_ERROR, MessageError.TRANSACTION_TO_9AY_ERROR), transactionResponse);
                    }

                    //9Pay checks then proceeds to transfer money from 9Pay to bank account B
                    rt = randomTime();
                    Thread.sleep(rt);

                    //transaction 9pay -> bank account B
                    transactionResponse.setTransferAmount(request.getAmount());
                    if (rt <= 1900) {
                        ninePayBankAccount -= request.getAmount();
                        bankAccountB.get().setActualBalance(bankAccountB.get().getActualBalance() + request.getAmount());
                        bankAccountB.get().setAvailableBalance(bankAccountB.get().getAvailableBalance() + request.getAmount());
                    }else{
                        //Transfer money back from 9Pay to bank account A
                        rt = randomTime();
                        Thread.sleep(rt);

                        transactionResponse.setStatus(TransactionStatus.FAIL);
                        internalFundTransfer(bankAccountA.get(), bankAccountB.get(), transactionResponse, feeAmount);

                        //transaction 9pay -> bank account B
                        if (rt <= 1900) {
                            ninePayBankAccount -= request.getAmount();
                            bankAccountA.get().setAvailableBalance(bankAccountA.get().getAvailableBalance() + request.getAmount());
                            bankAccountA.get().setActualBalance(bankAccountA.get().getActualBalance() - request.getAmount());
                        }else {
                            //email will be sent
                            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TRANSACTION_ERROR, "Failed To Transfer money from 9Pay to account A, money still in the 9pay account, email will be send to the technical team"), transactionResponse);
                        }

                        return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TRANSACTION_ERROR, MessageError.TRANSACTION_FROM_9AY_ERROR), transactionResponse);
                    }

                    //save transaction
                    //Update money on bank accounts
                    transactionResponse.setStatus(TransactionStatus.SUCCESS);
                    internalFundTransfer(bankAccountA.get(), bankAccountB.get(), transactionResponse, feeAmount);

                }else {
                    return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
                }
            } else {
                return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT, MessageError.TIMEOUT_ERROR));
            }
        } else {
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
        }

        return new CommonResponse(true, new ErrorResponse(GlobalErrorCode.SUCCESS, MessageError.SUCCESS), transactionResponse);
    }

    @Transactional
    public void internalFundTransfer(BankAccountEntity bankAccountA, BankAccountEntity bankAccountB, TransactionResponse transactionResponse, Integer feeAmount) {
        if(transactionResponse.getStatus() == TransactionStatus.SUCCESS){
            bankAccountRepository.save(bankAccountA);
            bankAccountRepository.save(bankAccountB);
        }

        TransactionEntity transactionEntity = new TransactionEntity(
                transactionResponse.getRequestId(),
                transactionResponse.getTransactionId(),
                transactionResponse.getRequestAmount(),
                transactionResponse.getTransferAmount(),
                feeAmount,
                transactionResponse.getContent(),
                transactionResponse.getType(),
                transactionResponse.getStatus(),
                new Date(),
                bankAccountA
        );
        transactionRepository.save(transactionEntity);
    }

    public CommonResponse getTransferInfoByTransactionId(TransactionIdRequest transactionIdRequest) throws InterruptedException {
        if (transactionIdRequest.getTransactionsId().isEmpty()) {
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.VALIDATE, MessageError.VALIDATE));
        }

        TransactionResponse transactionResponse = new TransactionResponse();

        //Random time connection
        Long rt = randomTime();
        Thread.sleep(rt);

        //partner -> 9pay
        if (rt <= 1900) {

            //get random partner id
            String partnerId = getSaltString(20);

            //set request id following above partner id
            String requestId = partnerId + "9P" + new SimpleDateFormat("yyyyMMDD").format(new Date()) + getSaltString(20);

            rt = randomTime();
            Thread.sleep(rt);

            //9pay -> partner
            if (rt <= 1900) {

                //set partner signature structure
                String partnerSignatureRequest = String.format("%s|%s|%s",
                        requestId,
                        partnerId,
                        transactionIdRequest.getTransactionsId()
                );

                transactionResponse.setRequestId(requestId);
                transactionResponse.setPartnerId(partnerId);
                transactionResponse.setSignature(partnerSignatureRequest);

                //get transaction info
                Optional<TransactionEntity> transaction = transactionRepository.findByCode(transactionIdRequest.getTransactionsId());
                if(transaction.isPresent()){
                    transactionResponse.setTransactionId(transaction.get().getCode());
                    transactionResponse.setBankNo(transaction.get().getAccount().getBank().getBankNo());
                    transactionResponse.setAccountNo(transaction.get().getAccount().getAccountNo());
                    transactionResponse.setAccountType(transaction.get().getAccount().getTypeAccountNo());
                    transactionResponse.setAccountName(transaction.get().getAccount().getAccountName());
                    transactionResponse.setRequestAmount(transaction.get().getRequestAmount());
                    transactionResponse.setTransferAmount(transaction.get().getTransferAmount());
                    transactionResponse.setContent(transaction.get().getContent());
                    transactionResponse.setStatus(transaction.get().getStatus());
                    transactionResponse.setCreatedAt(new SimpleDateFormat("yyyy-MM-DD hh:mm:ss").format(transaction.get().getCreatedAt()));

                }else {
                    return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TRANSACTION_NOT_FOUND, MessageError.TRANSACTION_NOT_FOUND),transactionResponse);
                }
            } else {
                return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
            }
        }else {
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
        }

        return new CommonResponse(true, new ErrorResponse(GlobalErrorCode.SUCCESS, MessageError.SUCCESS),transactionResponse);
    }

    public CommonResponse getTransferInfoByTransactionRequestId(TransactionRequestId request) throws InterruptedException {
        if (request.getTransactionRequestId().isEmpty()) {
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.VALIDATE, MessageError.VALIDATE));
        }

        TransactionResponse transactionResponse = new TransactionResponse();

        //Random time connection
        Long rt = randomTime();
        Thread.sleep(rt);

        //partner -> 9pay
        if (rt <= 1900) {

            //get random partner id
            String partnerId = getSaltString(20);

            //set request id following above partner id
            String requestId = partnerId + "9P" + new SimpleDateFormat("yyyyMMDD").format(new Date()) + getSaltString(20);

            rt = randomTime();
            Thread.sleep(rt);

            //9pay -> partner
            if (rt <= 1900) {

                //set partner signature structure
                String partnerSignatureRequest = String.format("%s|%s|%s",
                        requestId,
                        partnerId,
                        request.getTransactionRequestId()
                );

                transactionResponse.setRequestId(requestId);
                transactionResponse.setPartnerId(partnerId);
                transactionResponse.setSignature(partnerSignatureRequest);

                //get transaction info
                Optional<TransactionEntity> transaction = transactionRepository.findByTransactionRequestId(request.getTransactionRequestId());
                if(transaction.isPresent()){
                    transactionResponse.setTransactionRequestId(transaction.get().getTransactionRequestId());
                    transactionResponse.setTransactionId(transaction.get().getCode());
                    transactionResponse.setBankNo(transaction.get().getAccount().getBank().getBankNo());
                    transactionResponse.setAccountNo(transaction.get().getAccount().getAccountNo());
                    transactionResponse.setAccountType(transaction.get().getAccount().getTypeAccountNo());
                    transactionResponse.setAccountName(transaction.get().getAccount().getAccountName());
                    transactionResponse.setRequestAmount(transaction.get().getRequestAmount());
                    transactionResponse.setTransferAmount(transaction.get().getTransferAmount());
                    transactionResponse.setContent(transaction.get().getContent());
                    transactionResponse.setStatus(transaction.get().getStatus());
                    transactionResponse.setCreatedAt(new SimpleDateFormat("yyyy-MM-DD hh:mm:ss").format(transaction.get().getCreatedAt()));

                }else {
                    return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TRANSACTION_NOT_FOUND, MessageError.TRANSACTION_NOT_FOUND),transactionResponse);
                }
            } else {
                return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
            }
        }else {
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
        }

        return new CommonResponse(true, new ErrorResponse(GlobalErrorCode.SUCCESS, MessageError.SUCCESS),transactionResponse);
    }

    public void getBankList() {
        List<Bank> bank = new ArrayList<>();
        String uri = "https://api-console.9pay.vn/transfer-bank/bank-list";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> result = restTemplate.getForEntity(uri, Object.class);
    }
}

