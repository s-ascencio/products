package com.falabella.product.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductImage {

    private long id;
    private String url;
    private boolean principal;
    private long idProduct;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
