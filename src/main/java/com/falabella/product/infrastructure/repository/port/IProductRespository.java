package com.falabella.product.infrastructure.repository.port;

import com.falabella.product.domain.Product;

import java.util.List;

public interface IProductRespository {

    Product get(String sku);
    List<Product> getAll();
    long save(Product product);
    void update(Product product);
    boolean delete(String sku);
}
