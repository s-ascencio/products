package com.falabella.product.infrastructure.controller;

import com.falabella.product.application.port.IProductService;
import com.falabella.product.application.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService){
        this.productService = productService;
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductDTO> get(@PathVariable("sku") String sku) {
        return new ResponseEntity<>(productService.get(sku), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> save(@RequestBody @Valid ProductDTO productDto) {
        return new ResponseEntity<>(productService.save(productDto), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody @Valid ProductDTO productDto) {
        return new ResponseEntity<>(productService.update(productDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<String> delete(@PathVariable("sku") String sku) {
        return new ResponseEntity<>(productService.delete(sku), HttpStatus.OK);
    }


}
