package com.example.mott.Exception.User.Handler;


import com.example.mott.Exception.User.UserNotFoundException;
import com.example.mott.Utility.ResponseBuilder;
import com.example.mott.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<SimpleErrorResponse> userNotFoundHandler(UserNotFoundException e) {
        return ResponseBuilder.error(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
