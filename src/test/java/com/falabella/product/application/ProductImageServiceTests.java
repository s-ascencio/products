package com.falabella.product.application;

import com.falabella.product.domain.ProductImage;
import com.falabella.product.infrastructure.repository.port.IProductImageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductImageServiceTests {

    @Mock
    private IProductImageRepository productImageRepository;

    @InjectMocks
    private ProductImageService productImageService;

    private final ProductImage productImage = new ProductImage();
    private final List<ProductImage> images = new ArrayList<>();
    private final Map<String,Boolean> imageMaps = new HashMap<>();

    @BeforeEach
    void setupObject() {

        productImage.setId(1);
        productImage.setIdProduct(1);
        productImage.setUrl("https://falabella.scene7.com/is/image/Falabella/8406270_1");
        productImage.setPrincipal(true);

        images.add(productImage);

        imageMaps.put("https://falabella.scene7.com/is/image/Falabella/8406270_1",true);
    }

    @Test
    void shouldValidateGet() {
        when(productImageRepository.get(Mockito.anyLong())).thenReturn(images);
        Assertions.assertEquals(images, productImageService.get(1));
    }

    @Test
    void shouldValidateGetAll() {
        when(productImageRepository.getAll()).thenReturn(images);
        Assertions.assertEquals(images, productImageService.getAll());
    }

    @Test
    void shouldValidateGetPrincipalImageUrl() {
        Assertions.assertDoesNotThrow(() -> productImageService.getPrincipalImageUrl(images));
    }

    @Test
    void shouldValidateGetOtherImageUrl() {
        Assertions.assertDoesNotThrow(() -> productImageService.getOtherImagesUrl(images));
    }

    @Test
    void shouldValidateSave() {
        doNothing().when(productImageRepository).save(Mockito.any());
        Assertions.assertDoesNotThrow(() -> productImageService.save(imageMaps,1));
    }

    @Test
    void shouldValidateUpdate() {
        when(productImageRepository.delete(Mockito.anyLong())).thenReturn(true);
        doNothing().when(productImageRepository).save(Mockito.any());
        Assertions.assertDoesNotThrow(() -> productImageService.update(imageMaps,1));
    }
}
