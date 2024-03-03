package com.ecom.search.product.model;

import lombok.Data;

@Data
public class ProductDto {
    private String id;
    private String name;
    private String category;
    private String imageUrl;
    private String price;
    private String discountedPrice;
    private String description;
}
