package com.example.demo.services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.dtos.ProductDTO;
import com.example.demo.dtos.ProductImageDTO;
import com.example.demo.models.Product;
import com.example.demo.models.ProductImage;
import com.example.demo.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;

public interface IProductService {
    Product createProduct(ProductDTO productDto) throws DataNotFoundException, IOException;

    ProductProjection getProductById(Long id) throws DataNotFoundException;

    Product updateProductById(ProductDTO productDto, Long id) throws DataNotFoundException, IOException;

    void deleteProductById(Long id) throws DataNotFoundException;

    boolean existsByName(String name);

    Page<ProductProjection> getAllProduct(int page, int size);

    ProductImage uploadImage (ProductImageDTO productImageDTO) throws DataNotFoundException, IOException;
}
