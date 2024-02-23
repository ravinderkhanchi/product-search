package com.ecom.search.product.service;

import com.ecom.search.product.document.ProductDocument;
import com.ecom.search.product.exception.ApplicationException;
import com.ecom.search.product.model.ProductDto;
import com.ecom.search.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository = null;

    public ProductDto createPhoto(ProductDto document) {
        ProductDocument productDocumentEntity = getPhotoDocument(document);
        ProductDocument productDocument = productRepository.save(productDocumentEntity);
        return getPhotoDto(productDocument);
    }

    public ProductDto findById(String id) {
        Optional<ProductDocument> photoDocument = productRepository.findById(id);
        if (photoDocument.isPresent()) {
            return getPhotoDto(photoDocument.get());
        }
        throw new ApplicationException(HttpStatus.NOT_FOUND);
    }

    private ProductDto getPhotoDto(ProductDocument productDocument) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(productDocument, productDto);
        //productDto.setCategory("Shoes");
        //productDto.setName("Puma");
        //productDto.setImageUrl("https://th.bing.com/th/id/OIP.nQp2-Ryas00tk6n0tbT5igHaHa?pid=ImgDet&w=199&h=199&c=7&dpr=1.5");
        //productDto.setPrice("2000");
        //productDto.setDiscountedPrice("2200");
        return productDto;
    }

    private ProductDocument getPhotoDocument(ProductDto productDto) {
        ProductDocument productDocument = new ProductDocument();
        BeanUtils.copyProperties(productDto, productDocument);
        //productDocument.setId(UUID.randomUUID().toString());
        return productDocument;
    }

    public void updatePhoto(String id, ProductDto document) {
        findById(id);
        ProductDocument productDocumentEntity = getPhotoDocument(document);
        productRepository.save(productDocumentEntity);
    }

    public List<ProductDto> findAll() {
        Iterable<ProductDocument> photoDocuments = productRepository.findAll();
        //List<ProductDocument> photoDocuments = new ArrayList<>();
        return getPhotoDtos(photoDocuments);
    }

    public List<ProductDto> search(String key, String value) {
        List<ProductDocument> productDocuments = productRepository.findByUsingQuery(key, value);
        if (productDocuments.isEmpty()) {
            throw new ApplicationException(HttpStatus.NOT_FOUND);
        }
        return getPhotoDtos(productDocuments);
    }

    private List<ProductDto> getPhotoDtos(Iterable<ProductDocument> photoDocuments) {
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(getPhotoDto(new ProductDocument()));
        photoDocuments.forEach(e -> productDtoList.add(getPhotoDto(e)));
        return productDtoList;
    }

    public void deletePhoto(String id) {
        findById(id);
        productRepository.deleteById(id);
    }

}
