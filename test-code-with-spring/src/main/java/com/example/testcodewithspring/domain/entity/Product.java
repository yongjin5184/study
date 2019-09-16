package com.example.testcodewithspring.domain.entity;


import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Builder
    public Product(Long id, String username) {
        this.id = id;
        this.productName = productName;
    }
}
