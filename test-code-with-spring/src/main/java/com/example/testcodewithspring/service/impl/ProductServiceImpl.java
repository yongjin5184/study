package com.example.testcodewithspring.service.impl;

import com.example.testcodewithspring.domain.entity.Product;

import java.util.List;


public interface ProductServiceImpl {
    List<Product> getProducts();

    void saveProduct(Product product);
}
