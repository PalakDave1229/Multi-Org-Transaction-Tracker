package com.example.mott.Exception.Transaction.Handler;

import com.example.mott.Exception.Transaction.TransactionNotFoundException;
import com.example.mott.Utility.ResponseBuilder;
import com.example.mott.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<SimpleErrorResponse> transactionNotFoundHandler(TransactionNotFoundException e) {
        return ResponseBuilder.error(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
