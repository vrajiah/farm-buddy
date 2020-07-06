package com.farm.buddy.model;

import java.io.Serializable;

public class Error implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
    private String message;

    public Error(String errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
