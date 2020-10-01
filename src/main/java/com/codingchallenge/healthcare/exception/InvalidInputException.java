package com.codingchallenge.healthcare.exception;

import lombok.Getter;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@Getter
public class InvalidInputException extends Exception {

    private final String errorCode;

    public InvalidInputException(String errorCode,
                                 String message) {
        super(message);
        this.errorCode = errorCode;
    }


    public InvalidInputException(
            String errorCode, String message, Throwable t
    ) {
        super(message, t);
        this.errorCode = errorCode;
    }

}
