package com.codingchallenge.healthcare.exception;

import lombok.Getter;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@Getter
public class NoEnroleeFoundException extends Exception{

    private String errorCode;

    public NoEnroleeFoundException(String errorCode,
                                          String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public NoEnroleeFoundException(
            String errorCode, String message, Throwable t
    ){
        super(message, t);
        this.errorCode = errorCode;
    }

}
