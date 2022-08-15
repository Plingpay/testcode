package com.example.integrationbankservice.model.dto;

import lombok.Data;

@Data
public class Bank {
    private Long id;

    private String name;

    private String code;

    private String imageUrl;
}
