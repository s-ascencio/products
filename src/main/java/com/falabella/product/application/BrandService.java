package com.falabella.product.application;

import com.falabella.product.application.port.IBrandService;
import com.falabella.product.domain.Brand;
import com.falabella.product.infrastructure.repository.port.IBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService implements IBrandService {

    private final IBrandRepository brandRepository;

    @Autowired
    public BrandService(IBrandRepository brandRepository){
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand get(int id) {
        return brandRepository.get(id);
    }

    @Override
    public Brand get(String name) {
        return brandRepository.get(name);
    }

    @Override
    public List<Brand> getAll() {
        return brandRepository.getAll();
    }

    @Override
    public int save(Brand brand) {
        return brandRepository.save(brand);
    }
}
