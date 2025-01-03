package com.delivery_service.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse<T> {

    public static final String SUCCESS_STATUS = "success";
    public static final String FAIL_STATUS = "fail";

    private String status;
    private String message; //실패할 경우 전달될 메시지
    private T data;

}