package com.example.testcodewithspring.service;

import com.example.testcodewithspring.domain.entity.Product;
import com.example.testcodewithspring.domain.repository.ProductRepository;
import com.example.testcodewithspring.service.impl.ProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements ProductServiceImpl {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
