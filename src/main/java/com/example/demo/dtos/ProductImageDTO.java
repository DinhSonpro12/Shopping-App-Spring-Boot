package com.example.demo.dtos;

import org.springframework.web.multipart.MultipartFile;

public class ProductImageDTO {

    private MultipartFile url;
    private Long productId;

    public  ProductImageDTO(Long productId, MultipartFile url) {
        this.productId = productId;
        this.url = url;
    }

    // GETTER AND SETTER


    public MultipartFile getUrl() {
        return url;
    }

    public void setUrl(MultipartFile url) {
        this.url = url;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
