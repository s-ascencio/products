package com.falabella.product.application;

import com.falabella.product.domain.Brand;
import com.falabella.product.infrastructure.repository.port.IBrandRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandServiceTests {

    @Mock
    private IBrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    private final Brand brand = new Brand();
    private final List<Brand> brands = new ArrayList<>();

    @BeforeEach
    void setupObject() {
        brand.setId(1);
        brand.setName("NEW BALANCE");
    }

    @Test
    void shouldValidateGet() {
        when(brandRepository.get(Mockito.anyInt())).thenReturn(brand);
        Assertions.assertEquals(brand, brandService.get(1));
    }

    @Test
    void shouldValidateGetName() {
        when(brandRepository.get(Mockito.anyString())).thenReturn(brand);
        Assertions.assertEquals(brand, brandService.get("NEW BALANCE"));
    }

    @Test
    void shouldValidateGetAll() {
        when(brandRepository.getAll()).thenReturn(brands);
        Assertions.assertEquals(brands, brandService.getAll());
    }

    @Test
    void shouldValidateSave() {
        when(brandRepository.save(Mockito.any())).thenReturn(1);
        Assertions.assertEquals(1, brandService.save(brand));
    }
}
