package com.example.integrationbankservice.service;

import java.util.Random;

public abstract class BaseService {
    protected String getSaltString(Integer length) {
        String chars = "abcdefghijklmnipqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * chars.length());
            salt.append(chars.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    protected long randomTime(){
        long leftLimit = 0L;
        long rightLimit = 2000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
