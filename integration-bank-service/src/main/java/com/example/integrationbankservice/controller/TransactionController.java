package com.example.integrationbankservice.controller;

import com.example.integrationbankservice.model.dto.request.MoneyTransferRequest;
import com.example.integrationbankservice.model.dto.request.TransactionIdRequest;
import com.example.integrationbankservice.model.dto.request.TransactionRequestId;
import com.example.integrationbankservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/service/transfer")
@RequiredArgsConstructor
public class TransactionController{
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity moneyTransfer(@RequestBody MoneyTransferRequest moneyTransferRequest) throws InterruptedException{
        return ResponseEntity.ok(transactionService.moneyTransfer(moneyTransferRequest));
    }

    @GetMapping(value = "/info")
    public ResponseEntity transferInfo(@ModelAttribute TransactionIdRequest transactionIdRequest) throws InterruptedException {
        return ResponseEntity.ok(transactionService.getTransferInfoByTransactionId(transactionIdRequest));
    }

    @GetMapping("/request-info")
    public ResponseEntity transferInfo(@ModelAttribute TransactionRequestId transactionRequestId) throws InterruptedException {
        return ResponseEntity.ok(transactionService.getTransferInfoByTransactionRequestId(transactionRequestId));
    }
}
