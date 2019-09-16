package com.example.testcodewithspring.domain.repository;

import com.example.testcodewithspring.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {
}
