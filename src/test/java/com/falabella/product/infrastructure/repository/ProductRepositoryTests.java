package com.falabella.product.infrastructure.repository;

import com.falabella.product.domain.Product;
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
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProductRepository productRepository;

    private final Product product = new Product();
    private final List<Product> products = new ArrayList<>();
    private final LocalDateTime hoy = LocalDateTime.now();

    @BeforeEach
    void setupObject() {
        product.setId(1);
        product.setSku("FAL-8406270");
        product.setName("500 Zapatilla Urbana Mujer");
        product.setIdBrand(1);
        product.setSize("37");
        product.setPrice(42990.0);
        product.setCreatedAt(hoy);
        product.setUpdateAt(hoy);

        products.add(product);
    }


    @Test
    void shouldValidateGet() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Product>>any(), Mockito.anyString()))
                .thenReturn(products);
        Assertions.assertEquals(product, productRepository.get("FAL-8406270"));
    }

    @Test
    void shouldValidateGetAll() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Product>>any()))
                .thenReturn(products);
        Assertions.assertEquals(products, productRepository.getAll());
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

        Assertions.assertEquals(1, productRepository.save(product));
    }

    @Test
    void shouldValidateUpdate() {
        when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(KeyHolder.class))).thenReturn(1);
        Assertions.assertDoesNotThrow(() -> productRepository.update(product));
    }

    @Test
    void shouldValidateDelete() {
        when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyString())).thenReturn(1);
        Assertions.assertDoesNotThrow(() -> productRepository.delete("FAL-8406270"));
    }

    @Test
    void shouldValidateGetParameters() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Product>>any(), Mockito.anyString()))
                .thenReturn(products);

        Assertions.assertEquals("500 Zapatilla Urbana Mujer", productRepository.get("FAL-8406270").getName());
        Assertions.assertEquals("FAL-8406270", productRepository.get("FAL-8406270").getSku());
        Assertions.assertEquals("37", productRepository.get("FAL-8406270").getSize());
        Assertions.assertEquals(42990.0, productRepository.get("FAL-8406270").getPrice());
        Assertions.assertEquals(hoy, productRepository.get("FAL-8406270").getCreatedAt());
        Assertions.assertEquals(hoy, productRepository.get("FAL-8406270").getUpdateAt());
    }


}
