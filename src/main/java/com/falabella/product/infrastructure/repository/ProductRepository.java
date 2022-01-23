package com.falabella.product.infrastructure.repository;

import com.falabella.product.domain.Product;
import com.falabella.product.infrastructure.repository.port.IProductRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductRepository implements IProductRespository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product get(String sku) {
        String query = "SELECT id, sku, name, idbrand, size, price, createdat, updatedat FROM product WHERE sku = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Product.class), sku));
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT id, sku, name, idbrand, size, price, createdat, updatedat FROM product";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public long save(Product product) {
        String query = "INSERT INTO product (sku, name, size, price, idbrand) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setObject(1, product.getSku());
            ps.setObject(2, product.getName());
            ps.setObject(3, product.getSize());
            ps.setObject(4, product.getPrice());
            ps.setObject(5, product.getIdBrand());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public void update(Product product) {
        String query = "UPDATE product SET name = ?, size = ?, price = ?, idbrand = ? WHERE sku = ?";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" });
            ps.setObject(1, product.getName());
            ps.setObject(2, product.getSize());
            ps.setObject(3, product.getPrice());
            ps.setObject(4, product.getIdBrand());
            ps.setObject(5, product.getSku());
            return ps;
        }, keyHolder);

    }

    @Override
    public boolean delete(String sku) {
        String query = "DELETE FROM product WHERE sku = ?";
        Object[] params = {sku};
        int rows = jdbcTemplate.update(query, params);
        return rows == 1;
    }
}
