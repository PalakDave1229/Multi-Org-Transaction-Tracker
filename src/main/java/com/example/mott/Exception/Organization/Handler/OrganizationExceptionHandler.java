package com.example.mott.Exception.Organization.Handler;

import com.example.mott.Exception.Organization.OrganizationNotFoundException;
import com.example.mott.Utility.ResponseBuilder;
import com.example.mott.Utility.SimpleErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrganizationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<SimpleErrorResponse> organizationNotFoundHandler(OrganizationNotFoundException e) {
        return ResponseBuilder.error(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
