package com.falabella.product.application;

import com.falabella.product.application.dto.ProductDTO;
import com.falabella.product.application.port.IBrandService;
import com.falabella.product.application.port.IProductImageService;
import com.falabella.product.application.port.IProductService;
import com.falabella.product.domain.Brand;
import com.falabella.product.domain.Product;
import com.falabella.product.domain.ProductImage;
import com.falabella.product.infrastructure.repository.port.IProductRespository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final IProductRespository productRespository;
    private final IProductImageService productImageService;
    private final IBrandService brandService;
    private final ModelMapper modelMapper;


    @Autowired
    public ProductService(IProductRespository productRespository, IProductImageService productImageService,
                          IBrandService brandService, ModelMapper modelMapper){
        this.productRespository = productRespository;
        this.productImageService = productImageService;
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO get(String sku) {

        Product product = productRespository.get(sku);
        if (product == null) {
            throw new ApplicationException("Error. product does not exist");
        }

        ProductDTO productDto = modelMapper.map(product, ProductDTO.class);

        Brand brand = brandService.get(product.getIdBrand());
        productDto.setBrand(brand.getName());

        List<ProductImage> productImages = productImageService.get(product.getId());
        if (productImages.isEmpty()){
            throw new ApplicationException("Error. there are no product's images");
        }

        String principalImageUrl = productImageService.getPrincipalImageUrl(productImages);
        List<String> otherImagesUrl = productImageService.getOtherImagesUrl(productImages);
        productDto.setPrincipalImageUrl(principalImageUrl);
        productDto.setOtherImagesUrl(otherImagesUrl);

        return productDto;
    }

    @Override
    public List<ProductDTO> getAll() {

        CompletableFuture<List<Product>> futureProducts = CompletableFuture.supplyAsync(productRespository::getAll);
        CompletableFuture<List<Brand>> futureBrands = CompletableFuture.supplyAsync(brandService::getAll);
        CompletableFuture<List<ProductImage>> futureImages = CompletableFuture.supplyAsync(productImageService::getAll);

        return CompletableFuture.allOf(futureProducts, futureBrands, futureImages)
                .thenApplyAsync(s -> {
                    List<ProductDTO> productsDto = new ArrayList<>();

                    List<Product> products = futureProducts.join();
                    List<Brand> brands = futureBrands.join();
                    List<ProductImage> productImages = futureImages.join();

                    if (products.isEmpty()){
                        throw new ApplicationException("Error. there are no products");
                    }

                    for (Product product : products){
                        ProductDTO productDto = modelMapper.map(product, ProductDTO.class);

                        Optional<Brand> brand = brands.stream().filter(a -> a.getId() == product.getIdBrand()).findFirst();
                        brand.ifPresent(value -> productDto.setBrand(value.getName()));

                        List<ProductImage> filteredList = productImages.stream()
                                .filter(productImage -> productImage.getIdProduct() == product.getId())
                                .collect(Collectors.toList());

                        String principalImageUrl = productImageService.getPrincipalImageUrl(filteredList);
                        List<String> otherImagesUrl = productImageService.getOtherImagesUrl(filteredList);
                        productDto.setPrincipalImageUrl(principalImageUrl);
                        productDto.setOtherImagesUrl(otherImagesUrl);

                        productsDto.add(productDto);
                    }
                    return productsDto;
                }).join();


    }

    @Override
    @Transactional
    public String save(ProductDTO productDto) {

        Product product = productRespository.get(productDto.getSku());

        if (product != null){
            throw new ApplicationException("Error. product already exists");
        }

        skuValidator(productDto.getSku());
        product = modelMapper.map(productDto, Product.class);

        Brand brand = brandService.get(productDto.getBrand());
        if (brand == null) {
            brand = new Brand();
            brand.setName(productDto.getBrand());
            int id = brandService.save(brand);
            brand.setId(id);
        }
        product.setIdBrand(brand.getId());
        long id = productRespository.save(product);

        Map<String, Boolean> imageMaps = new HashMap<>();
        String principalImage = productDto.getPrincipalImageUrl();
        imageMaps.put(productDto.getPrincipalImageUrl(), true);

        List<String> otherImages = productDto.getOtherImagesUrl();
        if (otherImages != null && !otherImages.isEmpty()){
            for (String otherImage : otherImages) {
                if (principalImage.equals(otherImage)){
                    throw new ApplicationException("Error. principal image must be unique");
                }
                imageMaps.put(otherImage, false);
            }
        }

        productImageService.save(imageMaps, id);

        return "product has been created!";
    }

    @Override
    @Transactional
    public String update(ProductDTO productDto) {

        Product product = productRespository.get(productDto.getSku());

        if (product == null) {
            throw new ApplicationException("Error. product does not exist");
        }

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setSize(productDto.getSize());

        Brand brand = brandService.get(productDto.getBrand());
        if (brand == null){
            brand = new Brand();
            brand.setName(productDto.getBrand());
            int id = brandService.save(brand);
            brand.setId(id);
        }

        product.setIdBrand(brand.getId());
        productRespository.update(product);

        Map<String,Boolean> imageMaps = new HashMap<>();
        String principalImage = productDto.getPrincipalImageUrl();
        imageMaps.put(productDto.getPrincipalImageUrl(), true);

        List<String> otherImages = productDto.getOtherImagesUrl();
        if (otherImages != null && !otherImages.isEmpty()) {
            for (String otherImage : otherImages) {
                if (principalImage.equals(otherImage)) {
                    throw new ApplicationException("Error. principal image must be unique");
                }
                imageMaps.put(otherImage, false);
            }
        }

        productImageService.update(imageMaps, product.getId());

        return "product has been updated!";
    }

    @Override
    public String delete(String sku) {
        boolean isDeleted = productRespository.delete(sku);
        if (!isDeleted){
            throw new ApplicationException("Error. failed to delete product");
        }
        return "sku " + sku + " has been deleted!";
    }

    private void skuValidator(String sku){
        String fal = sku.substring(0,4);
        if (!fal.equals("FAL-")){
            throw new ApplicationException("Error. product's sku must start with FAL-");
        }
    }

}
