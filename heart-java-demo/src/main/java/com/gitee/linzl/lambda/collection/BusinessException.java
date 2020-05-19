package com.gitee.linzl.lambda.collection;


public class BusinessException extends RuntimeException {
    public BusinessException() {
        super();
    }
    public BusinessException(String message) {
        super(message);
    }
}
