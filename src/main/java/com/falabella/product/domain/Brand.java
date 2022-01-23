package com.falabella.product.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Brand {

    private int id;
    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
