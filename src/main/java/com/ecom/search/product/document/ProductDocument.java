package com.ecom.search.product.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * ProductDocument annotated with @{@link Document} where the @{@link Document#createIndex()} false prevents the spring data to
 * create and index during application boot
 */
@Data
@Document(indexName = "product", createIndex = false)
public class ProductDocument {
    @Id
    private String id;
    private String name;
    private String category;
    private String imageUrl;
    private String price;
    private String discountedPrice;
    private String description;

}
