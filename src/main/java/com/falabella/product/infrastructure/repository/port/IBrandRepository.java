package com.falabella.product.infrastructure.repository.port;

import com.falabella.product.domain.Brand;

import java.util.List;

public interface IBrandRepository {

    Brand get(int id);
    Brand get(String name);
    List<Brand> getAll();
    int save(Brand brand);
}
