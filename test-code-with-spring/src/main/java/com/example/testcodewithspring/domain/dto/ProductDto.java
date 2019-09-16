package com.example.testcodewithspring.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String productName;

    @Builder
    public ProductDto(Long id, String productName){
        this.id = id;
        this.productName = productName;
    }
}
