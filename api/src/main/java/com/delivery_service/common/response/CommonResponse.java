package com.delivery_service.common.response;

public class CommonResponse<T> {
    //필요할까?? 보류

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";

    private int code;
    private String message;
    private T data;


}