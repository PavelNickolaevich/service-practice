package com.practice.servicepractice.data.models;


import java.time.Instant;

public record ApiResponseService<T>(
        String status,
        String message,
        T data,
        String timestamp
) {

    public ApiResponseService(String status, String message, T data) {
        this(status, message, data, Instant.now().toString());
    }

    public ApiResponseService(String status, String message) {
        this(status, message, null, Instant.now().toString());
    }

}