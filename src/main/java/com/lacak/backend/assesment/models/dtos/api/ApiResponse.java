package com.lacak.backend.assesment.models.dtos.api;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private final boolean success;
    private final int status;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, HttpStatus.OK.value(), null, data);
    }

    public static <T> ApiResponse<T> error(int status, String message){
        return new ApiResponse<>(false, status, message, null);
    }

}
