package com.falabella.product.infrastructure.repository;


import com.falabella.product.domain.ProductImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductImageRepositoryTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProductImageRepository productImageRepository;

    private final ProductImage productImage = new ProductImage();
    private final List<ProductImage> images = new ArrayList<>();
    private final LocalDateTime hoy = LocalDateTime.now();

    @BeforeEach
    void setupObject() {
        productImage.setId(1);
        productImage.setIdProduct(1);
        productImage.setUrl("https://falabella.scene7.com/is/image/Falabella/8406270_1");
        productImage.setPrincipal(true);
        productImage.setCreatedAt(hoy);
        productImage.setUpdateAt(hoy);

        images.add(productImage);
    }

    @Test
    void shouldValidateGet() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<ProductImage>>any(), Mockito.anyLong()))
                .thenReturn(images);
        Assertions.assertEquals(images, productImageRepository.get(1));
    }

    @Test
    void shouldValidateGetAll() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<ProductImage>>any()))
                .thenReturn(images);
        Assertions.assertEquals(images, productImageRepository.getAll());
    }

    @Test
    void shouldValidateSave() {
        when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(GeneratedKeyHolder.class)))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    Map<String, Object> keyMap = new HashMap<>();
                    keyMap.put("id", 1);
                    ((GeneratedKeyHolder)args[1]).getKeyList().add(keyMap);
                    return 1;
                });
        Assertions.assertDoesNotThrow(() -> productImageRepository.save(productImage));
    }

    @Test
    void shouldValidateDelete() {
        when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyLong())).thenReturn(1);
        Assertions.assertDoesNotThrow(() -> productImageRepository.delete(1));
    }

    @Test
    void shouldValidateGetParameters() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<ProductImage>>any(), Mockito.anyLong()))
                .thenReturn(images);

        Assertions.assertEquals(1, productImageRepository.get(1).get(0).getId());
        Assertions.assertEquals(1, productImageRepository.get(1).get(0).getIdProduct());
        Assertions.assertEquals("https://falabella.scene7.com/is/image/Falabella/8406270_1", productImageRepository.get(1).get(0).getUrl());
        Assertions.assertTrue(productImageRepository.get(1).get(0).isPrincipal());
        Assertions.assertEquals(hoy, productImageRepository.get(1).get(0).getCreatedAt());
        Assertions.assertEquals(hoy, productImageRepository.get(1).get(0).getUpdateAt());
    }
}
