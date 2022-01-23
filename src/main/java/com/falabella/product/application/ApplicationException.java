package com.falabella.product.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class ApplicationException extends RuntimeException implements Serializable {

    public ApplicationException(String errorMessage){
        super(errorMessage);
    }
}
