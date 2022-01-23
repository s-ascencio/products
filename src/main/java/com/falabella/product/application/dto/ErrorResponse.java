package com.falabella.product.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String code;
    private String url;
    private String message;
}
