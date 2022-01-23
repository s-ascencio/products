package com.falabella.product.application;

import com.falabella.product.application.dto.ProductDTO;
import com.falabella.product.application.port.IBrandService;
import com.falabella.product.application.port.IProductImageService;
import com.falabella.product.domain.Brand;
import com.falabella.product.domain.Product;
import com.falabella.product.domain.ProductImage;
import com.falabella.product.infrastructure.repository.port.IProductRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

    @Mock
    private IProductRespository productRespository;
    @Mock
    private IProductImageService productImageService;
    @Mock
    private IBrandService brandService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;


    private final Product product = new Product();
    private final Brand brand = new Brand();
    private final ProductImage productImage = new ProductImage();
    private final List<ProductImage> images = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<Brand> brands = new ArrayList<>();
    private final ProductDTO productDto = new ProductDTO();
    private final List<String> otherImages = new ArrayList<>();

    @BeforeEach
    void setupObject() {

        product.setId(1);
        product.setSku("FAL-8406270");
        product.setName("500 Zapatilla Urbana Mujer");
        product.setIdBrand(1);
        product.setSize("37");
        product.setPrice(42990.0);

        brand.setId(1);
        brand.setName("NEW BALANCE");

        productImage.setId(1);
        productImage.setIdProduct(1);
        productImage.setUrl("https://falabella.scene7.com/is/image/Falabella/8406270_1");
        productImage.setPrincipal(true);

        images.add(productImage);
        products.add(product);
        brands.add(brand);

        productDto.setSku("FAL-8406270");
        productDto.setName("500 Zapatilla Urbana Mujer");
        productDto.setBrand("NEW BALANCE");
        productDto.setSize("37");
        productDto.setPrice(42990.0);
        productDto.setPrincipalImageUrl("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        otherImages.add("https://falabella.scene7.com/is/image/Falabella/8406270_2");
        productDto.setOtherImagesUrl(otherImages);

    }

    @Test
    void shouldValidateGet() {

        when(productRespository.get(Mockito.anyString())).thenReturn(product);

        when(modelMapper.map(Mockito.any(),Mockito.any())).thenReturn(new ProductDTO());

        when(brandService.get(Mockito.anyInt())).thenReturn(brand);

        when(productImageService.get(Mockito.anyLong())).thenReturn(images);

        when(productImageService.getPrincipalImageUrl(Mockito.anyList()))
                .thenReturn("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        when(productImageService.getOtherImagesUrl(Mockito.anyList()))
                .thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> productService.get("FAL-8406270"));
    }

    @Test
    void shouldValidateGetAll() {

        when(productRespository.getAll()).thenReturn(products);

        when(brandService.getAll()).thenReturn(brands);

        when(productImageService.getAll()).thenReturn(images);

        when(modelMapper.map(Mockito.any(),Mockito.any())).thenReturn(new ProductDTO());

        when(productImageService.getPrincipalImageUrl(Mockito.anyList()))
                .thenReturn("https://falabella.scene7.com/is/image/Falabella/8406270_1");

        when(productImageService.getOtherImagesUrl(Mockito.anyList()))
                .thenReturn(Collections.emptyList());

        Assertions.assertDoesNotThrow(() -> productService.getAll());
    }

    @Test
    void shouldValidateSave() {

        when(productRespository.get(Mockito.anyString())).thenReturn(null);

        when(modelMapper.map(Mockito.any(),Mockito.any())).thenReturn(new Product());

        when(brandService.get(Mockito.anyString())).thenReturn(null);

        when(brandService.save(Mockito.any())).thenReturn(1);

        when(productRespository.save(Mockito.any())).thenReturn(1L);

        doNothing().when(productImageService).save(Mockito.any(), Mockito.anyLong());

        Assertions.assertDoesNotThrow(() -> productService.save(productDto));

    }

    @Test
    void shouldValidateUpdate() {

        when(productRespository.get(Mockito.anyString())).thenReturn(product);

        when(brandService.get(Mockito.anyString())).thenReturn(null);

        when(brandService.save(Mockito.any())).thenReturn(1);

        doNothing().when(productRespository).update(Mockito.any());

        doNothing().when(productImageService).update(Mockito.any(), Mockito.anyLong());

        Assertions.assertDoesNotThrow(() -> productService.update(productDto));

    }

    @Test
    void shouldValidateDelete() {

        when(productRespository.delete(Mockito.anyString())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> productService.delete("FAL-8406270"));
    }

}
