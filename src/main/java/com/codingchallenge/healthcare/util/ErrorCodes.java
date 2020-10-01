package com.codingchallenge.healthcare.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@AllArgsConstructor
@Getter
public enum ErrorCodes {
    INVALID_INPUT("INVALID_INPUT", "Invalid input"),
    NO_RECORDS_FOUND("NO_RECORDS_FOUND", "No records found"),
    OPERATION_FAILED("OPERATION_FAILED", "Operation failed due to unknown internal error"),
    MALFORMED_JSON("MALFORMED_JSON", "Malformed json input");
    private final String code;
    private final String description;

}
