package com.falabella.product.infrastructure.repository;

import com.falabella.product.domain.Brand;
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
import org.mockito.stubbing.Answer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandRepositoryTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BrandRepository brandRepository;

    private final Brand brand = new Brand();
    private final List<Brand> brands = new ArrayList<>();

    private final LocalDateTime hoy = LocalDateTime.now();

    @BeforeEach
    void setupObject() {
        brand.setId(1);
        brand.setName("NEW BALANCE");
        brand.setCreatedAt(hoy);
        brand.setUpdateAt(hoy);

        brands.add(brand);
    }

    @Test
    void shouldValidateGet() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Brand>>any(), Mockito.anyInt()))
                .thenReturn(brands);
        Assertions.assertEquals(brand, brandRepository.get(1));
    }

    @Test
    void shouldValidateGetName() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Brand>>any(), Mockito.anyString()))
                .thenReturn(brands);
        Assertions.assertEquals(brand, brandRepository.get("NEW BALANCE"));
    }

    @Test
    void shouldValidateGetAll() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Brand>>any()))
                .thenReturn(brands);
        Assertions.assertEquals(brands, brandRepository.getAll());
    }

    @Test
    void shouldValidateSave() throws SQLException {

        when(jdbcTemplate.update(Mockito.any(PreparedStatementCreator.class), Mockito.any(GeneratedKeyHolder.class)))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    Map<String, Object> keyMap = new HashMap<>();
                    keyMap.put("id", 1);
                    ((GeneratedKeyHolder)args[1]).getKeyList().add(keyMap);
                    return 1;
                });

        Assertions.assertEquals(1, brandRepository.save(brand));
    }

    @Test
    void shouldValidateGetParameters() {
        when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<BeanPropertyRowMapper<Brand>>any(), Mockito.anyString()))
                .thenReturn(brands);

        Assertions.assertEquals(1, brandRepository.get("FAL-8406270").getId());
        Assertions.assertEquals("NEW BALANCE", brandRepository.get("FAL-8406270").getName());
        Assertions.assertEquals(hoy, brandRepository.get("FAL-8406270").getCreatedAt());
        Assertions.assertEquals(hoy, brandRepository.get("FAL-8406270").getUpdateAt());
    }
}
