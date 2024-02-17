package com.ecom.search.product.repository;

import com.ecom.search.product.document.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductDocument, String> {

    Page<ProductDocument> findByCategory(String category, Pageable pageable);

    Page<ProductDocument> findByName(String name, Pageable pageable);

    Page<ProductDocument> findById(String title, Pageable pageable);

    @Query("{\"match\": {\"?0\": \"?1\"}}")
    List<ProductDocument> findByUsingQuery(String key, String value);
}
