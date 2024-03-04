package com.ecom.search.product.controller;

import com.ecom.search.product.model.ProductDto;
import com.ecom.search.product.model.ProductResponseDto;
import com.ecom.search.product.service.ProductService;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service = null;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createPhoto(@RequestBody ProductDto document) {
        return new ResponseEntity<>(service.createPhoto(document), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> updatePhoto(@PathVariable String id, @RequestBody
    ProductDto document) {
        document.setId(id);
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> findById(@PathVariable String id) {
        ProductDto byId = service.findById(id);
        if(Objects.nonNull(byId)){
            return new ResponseEntity<>(byId, HttpStatus.OK);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ProductResponseDto> findAll() {
        return new ResponseEntity<>(new ProductResponseDto(service.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponseDto> search(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        return new ResponseEntity<>(new ProductResponseDto(service.search(key, value)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String id) {
        service.deletePhoto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
