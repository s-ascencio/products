package com.falabella.product.infrastructure.repository.port;

import com.falabella.product.domain.ProductImage;

import java.util.List;

public interface IProductImageRepository {

    List<ProductImage> get(long idProduct);
    List<ProductImage> getAll();
    void save(ProductImage productImage);
    boolean delete(long idProducto);
}
