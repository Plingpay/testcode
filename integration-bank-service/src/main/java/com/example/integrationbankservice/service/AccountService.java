package com.example.integrationbankservice.service;

import com.example.integrationbankservice.base.MessageError;
import com.example.integrationbankservice.base.GlobalErrorCode;
import com.example.integrationbankservice.model.dto.request.AccountVerifyRequest;
import com.example.integrationbankservice.model.dto.response.BalanceInfoResponse;
import com.example.integrationbankservice.model.dto.response.CommonResponse;
import com.example.integrationbankservice.model.dto.response.ErrorResponse;
import com.example.integrationbankservice.model.dto.response.VerifyResponse;
import com.example.integrationbankservice.model.entity.BankAccountEntity;
import com.example.integrationbankservice.model.entity.BankEntity;
import com.example.integrationbankservice.repository.BankAccountRepository;
import com.example.integrationbankservice.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService extends BaseService{
    private final BankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;

    public CommonResponse verifyAccount(AccountVerifyRequest request) throws InterruptedException {
        if(request.getAccountNo().isEmpty() || request.getBankNo().isEmpty() || request.getAccountType() == null || (request.getAccountType() != 0 && request.getAccountType() != 1)){
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.VALIDATE, MessageError.VALIDATE));
        }

        VerifyResponse verifyResponse = new VerifyResponse();

        long rt = randomTime();

        Thread.sleep(rt);
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
                String signatureRequest = String.format("%s|%s|%s|%s|%d",
                        requestId,
                        partnerId,
                        request.getBankNo(),
                        request.getAccountNo(),
                        request.getAccountType()
                );

                //sign the transmitted data using RSA algorithm, then send it in base64 encoding

                rt = randomTime();
                Thread.sleep(rt);

                //partner -> 9pay
                if (rt <= 1900) {

                    //Check bank exists
                    Optional<BankEntity> bank = bankRepository.findByBankNo(request.getBankNo());
                    if(!bank.isPresent()){
                        return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.INVALID_BANK,MessageError.INVALID_BANK));
                    }

                    //Find bank account with search condition
                    Optional<BankAccountEntity> bankAccount = bankAccountRepository.findByAccountNoAndTypeAccountNo(request.getAccountNo(), request.getAccountType());
                    if(!bankAccount.isPresent()){
                        return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.BANK_ACCOUNT_NOT_FOUND, MessageError.BANK_ACCOUNT_NOT_FOUND));
                    }

                    verifyResponse.setRequestId(requestId);
                    verifyResponse.setPartnerId(partnerId);
                    verifyResponse.setBankNo(bank.get().getBankNo());
                    verifyResponse.setAccountNo(bankAccount.get().getAccountNo());
                    verifyResponse.setAccountType(bankAccount.get().getType());
                    verifyResponse.setAccountName(bankAccount.get().getAccountName());
                    verifyResponse.setSignature(String.format("%s|%s|%s|%s|%d|%s",
                            requestId,
                            partnerId,
                            request.getBankNo(),
                            request.getAccountNo(),
                            request.getAccountType(),
                            bankAccount.get().getAccountName()
                    ));

                } else {
                    return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
                }
            } else {
                return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
            }
        } else {
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
        }

        return new CommonResponse(true, new ErrorResponse(GlobalErrorCode.SUCCESS,MessageError.SUCCESS), verifyResponse);
    }

    public CommonResponse searchBalance() throws InterruptedException {
        BalanceInfoResponse balanceInfoResponse = new BalanceInfoResponse();

        String requestTime = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss").format(new Date());
        long rt = randomTime();

        Thread.sleep(rt);
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
                String signatureRequest = String.format("%s|%s|%s",
                        requestId,
                        partnerId,
                        requestTime
                );

                //sign the transmitted data using RSA algorithm, then send it in base64 encoding

                rt = randomTime();
                Thread.sleep(rt);

                //partner -> 9pay
                if (rt <= 1900) {
                    String responseTime = new SimpleDateFormat("yyyy-MM-DD hh:mm:ss").format(new Date());

                    balanceInfoResponse.setRequestTime(requestTime);
                    balanceInfoResponse.setResponseTime(responseTime);

                    List<BankAccountEntity> bankAccounts = bankAccountRepository.findAll();
                    if(bankAccounts.size() == 0){
                        return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.BANK_ACCOUNT_NOT_FOUND,MessageError.BANK_ACCOUNT_NOT_FOUND), balanceInfoResponse);
                    }

                    balanceInfoResponse.setAccountEntityList(bankAccounts);
                }else {
                    return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
                }
            }else {
                return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
            }
        }else {
            return new CommonResponse(false, new ErrorResponse(GlobalErrorCode.TIMEOUT,MessageError.TIMEOUT_ERROR));
        }

        return new CommonResponse(true, new ErrorResponse(GlobalErrorCode.SUCCESS,MessageError.SUCCESS), balanceInfoResponse);
    }
}
