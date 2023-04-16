package com.project.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse {
    private String message;

    private HttpStatus status;

    private LocalDateTime timeStamp;

    public ExceptionResponse(String message, HttpStatus status){
        this.message=message;
        this.status=status;
        timeStamp=LocalDateTime.now();
    }
}
