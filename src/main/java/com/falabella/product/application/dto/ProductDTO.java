package com.falabella.product.application.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
public class ProductDTO {

    @NotNull(message = "The product's SKU cannot be null.")
    private String sku;

    @NotNull(message = "The product's SKU cannot be null.")
    @NotBlank(message = "The product's name cannot be blank")
    @Size(min = 3, max = 50, message = "The product's name must contain between 3 and 50 characters.")
    private String name;

    @NotNull(message = "The product's SKU cannot be null.")
    @NotBlank(message = "The product's name cannot be blank")
    @Size(min = 3, max = 50, message = "The product's name must contain between 3 and 50 characters.")
    private String brand;

    @NotBlank(message = "The product's size cannot be blank.")
    private String size;

    @NotNull(message = "The product's price cannot be null.")
    @DecimalMin(value = "1.00", message = "The product's price must be greater than or equal to 1.00")
    @DecimalMax(value = "99999999.00", message = "The product's price must be less than or equal to 99999999.00")
    @Digits(integer=8, fraction=2)
    private double price;

    @NotNull(message = "The product's principalImage cannot be null.")
    @NotBlank(message = "The product's principalImage cannot be blank")
    private String principalImageUrl;

    private List<String> otherImagesUrl;
}
