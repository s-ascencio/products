package com.falabella.product.application.port;

import com.falabella.product.domain.Brand;

import java.util.List;

public interface IBrandService {

    Brand get(int id);
    Brand get(String name);
    List<Brand> getAll();
    int save(Brand brand);
}
