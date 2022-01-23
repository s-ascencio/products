package com.falabella.product.application.port;

import com.falabella.product.application.dto.ProductDTO;

import java.util.List;

public interface IProductService {

    ProductDTO get(String sku);
    List<ProductDTO> getAll();
    String save(ProductDTO productDTO);
    String update(ProductDTO productDTO); //TODO: hacer boolean
    String delete(String sku);
}
