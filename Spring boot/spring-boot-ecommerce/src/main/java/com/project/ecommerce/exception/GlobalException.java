package com.project.ecommerce.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@Getter
@Setter
public class GlobalException extends RuntimeException {

    private HttpStatus status;

    private int statusCode;


    public GlobalException(String message,HttpStatus status,int statusCode) {
        super(message);
        this.status=status;
        this.statusCode=statusCode;
    }


}
