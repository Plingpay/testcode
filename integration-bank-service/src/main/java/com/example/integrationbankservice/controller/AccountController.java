package com.example.integrationbankservice.controller;

import com.example.integrationbankservice.model.dto.request.AccountVerifyRequest;
import com.example.integrationbankservice.model.dto.response.VerifyResponse;
import com.example.integrationbankservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/service/account")
@RequiredArgsConstructor
public class AccountController{
    private final AccountService accountService;

    @GetMapping("/verify")
    public ResponseEntity verify(@ModelAttribute AccountVerifyRequest accountVerifyRequest) throws InterruptedException {
        log.info("verify account");
        return ResponseEntity.ok(accountService.verifyAccount(accountVerifyRequest));
    }

    @GetMapping("/balance")
    public ResponseEntity searchBalance() throws InterruptedException {
        log.info("verify account");
        return ResponseEntity.ok(accountService.searchBalance());
    }
}
