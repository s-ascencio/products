package com.falabella.product.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Product {

    private long id;
    private String sku;
    private String name;
    private String size;
    private double price;

    private int idBrand;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
