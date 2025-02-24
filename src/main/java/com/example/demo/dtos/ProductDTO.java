package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
    private String name;
    private float price;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
    private MultipartFile thumbnail;
//    private List<MultipartFile> files;

//    GETTER AND SETTER

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long category_id) {
        this.categoryId = category_id;
    }

    public MultipartFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MultipartFile thumbnail) {
        this.thumbnail = thumbnail;
    }

//    public List<MultipartFile> getFiles() {
//        return files;
//    }
//
//    public void setFiles(List<MultipartFile> files) {
//        this.files = files;
//    }

//    public void setFile(MultipartFile file) {
//        this.file = file;
//    }


}
