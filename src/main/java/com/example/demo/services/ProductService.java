package com.example.demo.services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.dtos.ProductDTO;
import com.example.demo.dtos.ProductImageDTO;
import com.example.demo.models.Product;
import com.example.demo.models.ProductImage;
import com.example.demo.projections.ProductProjection;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ProductImageRepository;
import com.example.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductImageRepository productImageRepository;


    @Override
    public Product createProduct(ProductDTO productDto) throws DataNotFoundException, IOException {
        categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category have id " + productDto.getCategoryId() + " not found"));

        Product newProduct = mapToProduct(productDto);

        if (productDto.getThumbnail() != null && !productDto.getThumbnail().isEmpty()) {
            String thumbnailName = storeFile(productDto.getThumbnail());
            newProduct.setThumbnail(thumbnailName);
        } else {
            newProduct.setThumbnail(""); // hoặc giá trị mặc định
        }

        return productRepository.save(newProduct);
    }

    @Override
    public ProductProjection getProductById(Long id) throws DataNotFoundException {
//        return productRepository.findById(id)
        return productRepository.findProjectionById(id)
                .orElseThrow(() -> new DataNotFoundException("Product with id = " + id + "not found"));
    }

    @Override
    public Product updateProductById(ProductDTO productDto, Long id) throws DataNotFoundException, IOException {
        Product existProduct = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found product with id: " + id));

        Product updatedProduct = mapToProduct(productDto);
        if (productDto.getThumbnail() != null && !productDto.getThumbnail().isEmpty()) {
            String thumbnailName = storeFile(productDto.getThumbnail());
            updatedProduct.setThumbnail(thumbnailName);
        } else {
            updatedProduct.setThumbnail(""); // hoặc giá trị mặc định
        }

        updatedProduct.setId(id);

        return productRepository.save(updatedProduct);
    }

    @Override
    public void deleteProductById(Long id) throws DataNotFoundException {

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new DataNotFoundException("Product not found");
        }
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public Page<ProductProjection> getAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return productRepository.findAllBy(pageable);
    }

    @Override
    public ProductImage uploadImage(ProductImageDTO productImageDTO) throws DataNotFoundException, IOException {
        Product exitsProduct = productRepository.findById(productImageDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Not found product with id: " + productImageDTO.getProductId()));

        ProductImage productImage = new ProductImage();
        String newFileName = storeFile(productImageDTO.getUrl());

        productImage.setUrl(newFileName);
        productImage.setProduct(exitsProduct);

        return productImageRepository.save(productImage);
    }

    private Product mapToProduct(ProductDTO productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()).get());
        return product;
    }

    private String storeFile(MultipartFile thumbnail) throws IOException {
        String newFileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(thumbnail.getOriginalFilename());
        Path uploadDir = Paths.get("uploads");
        Files.createDirectories(uploadDir);

        Path destination = Paths.get("uploads/" + newFileName);
        Files.copy(thumbnail.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }
}

