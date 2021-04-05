package com.contract.exception;

public class BaseException extends Exception {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BaseException() {
        super();
    }
    public BaseException(String msg, int code) {
        super(msg);
        this.code = code;
    }
}
