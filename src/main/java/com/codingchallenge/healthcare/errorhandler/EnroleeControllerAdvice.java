package com.codingchallenge.healthcare.errorhandler;

import com.codingchallenge.healthcare.api.EnroleeApi;
import com.codingchallenge.healthcare.dto.EnroleeApiResponse;
import com.codingchallenge.healthcare.exception.InvalidInputException;
import com.codingchallenge.healthcare.exception.NoEnroleeFoundException;
import com.codingchallenge.healthcare.util.ErrorCodes;
import com.mongodb.lang.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Sravani
 * @version $ 9/30/20
 */
@Slf4j
@ControllerAdvice(basePackageClasses = {EnroleeApi.class})
public class EnroleeControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidInputException.class})
    public ResponseEntity<EnroleeApiResponse> handleInvalidInputException(
            InvalidInputException ex){
        log.error("Invalid input exception occured ",ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new EnroleeApiResponse(ErrorCodes.INVALID_INPUT.getCode(),
                                ex.getMessage(), null)
                );
    }

    @ExceptionHandler(value = {NoEnroleeFoundException.class})
    public ResponseEntity<EnroleeApiResponse> handleNoRecordsFoundException(
            NoEnroleeFoundException ex){
        log.error("No enrolee found exception occured ",ex);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new EnroleeApiResponse(ErrorCodes.NO_RECORDS_FOUND.getCode(),
                                ex.getMessage(), null)
                );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<EnroleeApiResponse> handleIllegalArgumentException(
            IllegalArgumentException ex){
        log.error("IllegalArgumentexception occured ",ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new EnroleeApiResponse(ErrorCodes.INVALID_INPUT.getCode(),
                                ex.getMessage(), null)
                );
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        log.error("methodArgumentexception occured ",ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new EnroleeApiResponse(ErrorCodes.INVALID_INPUT.getCode(),
                                ErrorCodes.INVALID_INPUT.getDescription() ,
                                extractFieldErrors(ex.getBindingResult()))
                );
    }


    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException ex
    ) {
        log.error("constraintviolationexception occured ",ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        new EnroleeApiResponse(ErrorCodes.INVALID_INPUT.getCode(),
                                ErrorCodes.INVALID_INPUT.getDescription() ,
                                null)
                );
    }

    private Map<String, String> extractFieldErrors(BindingResult bindingResult){
        return bindingResult.getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> Optional.ofNullable(error.getDefaultMessage()).orElse("")
                        )
                );
    }

}
