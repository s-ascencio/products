package com.falabella.product.infrastructure.repository;

import com.falabella.product.domain.Brand;
import com.falabella.product.infrastructure.repository.port.IBrandRepository;
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
public class BrandRepository implements IBrandRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BrandRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Brand get(int id) {
        String query = "SELECT id, name, createdat, updatedat FROM brand WHERE id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Brand.class), id));
    }

    @Override
    public Brand get(String name) {
        String query = "SELECT id, name, createdat, updatedat FROM brand WHERE name = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Brand.class), name));
    }

    @Override
    public List<Brand> getAll() {
        String query = "SELECT id, name, createdat, updatedat FROM brand";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Brand.class));
    }

    @Override
    public int save(Brand brand) {
        String query = "INSERT INTO brand (name) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[]{"id"});
            ps.setObject(1, brand.getName());
            return ps;
        }, keyHolder);

        return (int) Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
