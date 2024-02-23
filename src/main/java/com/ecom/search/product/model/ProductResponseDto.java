package com.ecom.search.product.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    List<ProductDto> products;

    public List<ProductDto> getProducts(){
        return products.stream().filter(productDto -> Objects.nonNull(productDto.getImageUrl())).collect(
            Collectors.toList());
    }

}
