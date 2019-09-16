package com.example.testcodewithspring.controller;

import com.example.testcodewithspring.domain.dto.ProductDto;
import com.example.testcodewithspring.domain.entity.Product;
import com.example.testcodewithspring.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contents")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ModelMapper modelMapper;

    @GetMapping("/product")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @PostMapping("/product")
    public void insertProduct(@RequestBody ProductDto productDto){
        productService.saveProduct(modelMapper.map(productDto, Product.class));
    }
}
