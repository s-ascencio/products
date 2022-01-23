package com.falabella.product.infrastructure.repository;

import com.falabella.product.domain.ProductImage;
import com.falabella.product.infrastructure.repository.port.IProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ProductImageRepository implements IProductImageRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductImageRepository(JdbcTemplate jdbcTemplate){ this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<ProductImage> get(long idProduct) {
        String query = "SELECT id, url, principal, idproduct, createdat, updatedat FROM productimage WHERE idproduct = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ProductImage.class), idProduct);
    }

    @Override
    public List<ProductImage> getAll() {
        String query = "SELECT id, url, principal, idproduct, createdat, updatedat FROM productimage";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(ProductImage.class));
    }

    @Override
    public void save(ProductImage productImage) {
        String query = "INSERT INTO productimage (url, principal, idproduct) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setObject(1, productImage.getUrl());
            ps.setObject(2, productImage.isPrincipal());
            ps.setObject(3, productImage.getIdProduct());
            return ps;
        }, keyHolder);
    }

    @Override
    public boolean delete(long idProduct) {
        String query = "DELETE FROM productimage WHERE idproduct = ?";
        Object[] params = {idProduct};
        int rows = jdbcTemplate.update(query, params);
        return rows >= 1;
    }

}
