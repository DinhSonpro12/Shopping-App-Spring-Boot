package com.example.demo.projections;

import java.util.List;

public class AllProductResponse {
    private List<ProductProjection> products;
    private int totalPages;

    public AllProductResponse(List<ProductProjection> products, int totalPages) {
        this.products = products;
        this.totalPages = totalPages;
    }

    public List<ProductProjection> getProducts() {
        return products;
    }

    public void setProducts(List<ProductProjection> products) {
        this.products = products;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
