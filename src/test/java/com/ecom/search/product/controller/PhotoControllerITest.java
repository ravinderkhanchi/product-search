package com.ecom.search.product.controller;

import com.ecom.search.product.Application;
import com.ecom.search.product.model.ProductDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Disabled
class PhotoControllerITest {

    @Autowired
    private ProductController photoController = null;

    @Test
    void createPhotoTest() {
        ProductDto productDto = createPhotoDtoResponseEntity();
        assertNotNull(productDto);
    }

    @Test
    void getPhotoTest() {
        ProductDto productDto = createPhotoDtoResponseEntity();
        ResponseEntity<ProductDto> responseEntity = photoController.findById(productDto.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        responseEntity = photoController.findById(UUID.randomUUID().toString());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getAllPhotoTest() {
        createPhotoDtoResponseEntity();
        ResponseEntity<List<ProductDto>> responseEntity = photoController.findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    void updatePhotoTest() {
        ProductDto productDto = createPhotoDtoResponseEntity();
        String updatedTitle = "Title Updated";
        productDto.setName(updatedTitle);
        ResponseEntity<Void> responseEntity = photoController.updatePhoto(productDto.getId(),
            productDto);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        ResponseEntity<ProductDto> byId = photoController.findById(productDto.getId());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(byId.getBody());
        assertEquals(updatedTitle, byId.getBody().getName());
        responseEntity = photoController.updatePhoto(UUID.randomUUID().toString(), productDto);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void deletePhotoTest() {
        ProductDto productDto = createPhotoDtoResponseEntity();
        ResponseEntity<Void> responseEntity = photoController.deletePhoto(productDto.getId());
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        responseEntity = photoController.deletePhoto(UUID.randomUUID().toString());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void searchPhotoTest() {
        ProductDto productDto = createPhotoDtoResponseEntity();
        ResponseEntity<List<ProductDto>> responseEntity = photoController.search("name", productDto.getName());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        Optional<ProductDto> any = responseEntity.getBody().stream().findAny();
        assertTrue(any.isPresent());
        assertEquals(productDto.getId(), any.get().getId());
        responseEntity = photoController.search("title", UUID.randomUUID().toString());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    private ProductDto createPhotoDtoResponseEntity() {
        ProductDto productDto = new ProductDto();
        productDto.setImageUrl(UUID.randomUUID().toString());
        productDto.setName(UUID.randomUUID().toString());
        productDto.setCategory(UUID.randomUUID().toString());
        ResponseEntity<ProductDto> photoDtoResponseEntity = photoController.createPhoto(productDto);
        assertNotNull(photoDtoResponseEntity);
        assertNotNull(photoDtoResponseEntity.getBody());
        assertEquals(HttpStatus.CREATED, photoDtoResponseEntity.getStatusCode());
        return photoDtoResponseEntity.getBody();
    }
}
