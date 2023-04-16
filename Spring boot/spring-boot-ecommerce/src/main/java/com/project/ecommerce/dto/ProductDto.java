package com.project.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    private String id;
    private String name;

    private String description;

    private String sku;

    private String unitPrice;

    private String imageUrl;

    private boolean active;

    private int unitsInStock;

    private CategoryDto category;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdatedDate;
}
