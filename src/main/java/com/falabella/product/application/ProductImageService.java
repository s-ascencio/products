package com.falabella.product.application;

import com.falabella.product.application.port.IProductImageService;
import com.falabella.product.domain.ProductImage;
import com.falabella.product.infrastructure.repository.port.IProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductImageService implements IProductImageService {

    private final IProductImageRepository productImageRepository;

    @Autowired
    public ProductImageService(IProductImageRepository productImageRepository){
        this.productImageRepository = productImageRepository;
    }

    @Override
    public List<ProductImage> get(long idProduct) {
        return productImageRepository.get(idProduct);
    }

    @Override
    public List<ProductImage> getAll() {
        return productImageRepository.getAll();
    }

    @Override
    public String getPrincipalImageUrl(List<ProductImage> images) {
        Optional<ProductImage> productImage = images.stream().filter(ProductImage::isPrincipal).findFirst();
        return productImage.map(ProductImage::getUrl)
                .orElseThrow(() -> new ApplicationException("Error. there is no principal image"));
    }

    @Override
    public List<String> getOtherImagesUrl(List<ProductImage> images) {
        return images.stream().filter(productImage -> !productImage.isPrincipal())
                .map(ProductImage::getUrl)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Map<String,Boolean> imageMaps , long idProduct) {
        for (Map.Entry<String, Boolean> pair : imageMaps.entrySet()) {
            ProductImage productImage = new ProductImage();
            productImage.setUrl(pair.getKey());
            productImage.setPrincipal(pair.getValue());
            productImage.setIdProduct(idProduct);
            productImageRepository.save(productImage);
        }
    }

    @Override
    public void update(Map<String, Boolean> imageMaps, long idProduct) {
        boolean isDelete = productImageRepository.delete(idProduct);
        if (!isDelete){
            throw new ApplicationException("Error. failed to update product");
        }
        save(imageMaps,idProduct);

    }
}
