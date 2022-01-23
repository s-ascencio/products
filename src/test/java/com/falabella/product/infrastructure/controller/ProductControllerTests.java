package com.falabella.product.infrastructure.controller;

import com.falabella.product.application.dto.ProductDTO;
import com.falabella.product.application.port.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class ProductControllerTests {

    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    private static final String URL_PREFIX = "/products";
    private static final String SKU = "FAL-8406270";

    private final ProductDTO productDto = new ProductDTO();

    @BeforeEach
    void setupObject() {

        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        productDto.setSku("FAL-8406270");
        productDto.setName("500 Zapatilla Urbana Mujer");
        productDto.setBrand("NEW BALANCE");
        productDto.setSize("37");
        productDto.setPrice(42990.0);
        productDto.setPrincipalImageUrl("https://falabella.scene7.com/is/image/Falabella/8406270_1");

    }

    @Test
    void shouldValidateGet() throws Exception {
        MvcResult result = this.mockMvc.perform(get(URL_PREFIX + "/" + SKU)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(200, statusCode);
    }

    @Test
    void shouldValidateGetAll() throws Exception {
        MvcResult result = this.mockMvc.perform(get(URL_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(200, statusCode);
    }

    @Test
    void shouldValidateSave() throws Exception {
        MvcResult result = this.mockMvc.perform(post(URL_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andDo(print())
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(201, statusCode);
    }

    @Test
    void shouldValidateUpdate() throws Exception {
        MvcResult result = this.mockMvc.perform(put(URL_PREFIX)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andDo(print())
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(201, statusCode);
    }

    @Test
    void shouldValidateDelete() throws Exception {
        MvcResult result = this.mockMvc.perform(delete(URL_PREFIX + "/" + SKU)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productDto)))
                .andDo(print())
                .andReturn();

        int statusCode = result.getResponse().getStatus();
        Assertions.assertEquals(200, statusCode);
    }

}
