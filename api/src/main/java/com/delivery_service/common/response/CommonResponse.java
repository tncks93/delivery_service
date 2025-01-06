package com.delivery_service.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@AllArgsConstructor
public class CommonResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";

    private String status;
    private String message; //실패할 경우 전달될 메시지
    private T data;

    private CommonResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    private CommonResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<T>(SUCCESS_STATUS,data);
    }

    public static <T> CommonResponse<T> fail(String message) {
        return new CommonResponse<T>(FAIL_STATUS,message);
    }

}