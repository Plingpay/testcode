package com.example.integrationbankservice.model.dto.response;

import lombok.*;

import java.lang.reflect.InvocationTargetException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {
    private Boolean success;
    private ErrorResponse error;
    private Object data;

    public CommonResponse(Boolean success, ErrorResponse error) {
        this.success = success;
        this.error = error;
    }
}