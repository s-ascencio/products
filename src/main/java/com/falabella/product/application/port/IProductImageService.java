package com.falabella.product.application.port;

import com.falabella.product.domain.ProductImage;

import java.util.List;
import java.util.Map;

public interface IProductImageService {

    List<ProductImage> get(long idProduct);
    List<ProductImage> getAll();
    String getPrincipalImageUrl(List<ProductImage> images);
    List<String> getOtherImagesUrl(List<ProductImage> images);
    void save(Map<String,Boolean> imageMaps , long idProduct);
    void update(Map<String,Boolean> imageMaps, long idProduct);
}
