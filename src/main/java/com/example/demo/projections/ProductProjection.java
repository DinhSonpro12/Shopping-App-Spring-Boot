package com.example.demo.projections;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "price", "thumbnail", "category"}) // Định nghĩa thứ tự
public interface ProductProjection {
    Long getId();
    String getName();
    Double getPrice();
    String getThumbnail();
    CategoryProjection getCategory();
}
