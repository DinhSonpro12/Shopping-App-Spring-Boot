package com.example.demo.repositories;

import com.example.demo.models.Product;
import com.example.demo.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable); //phân trang

    //    <T> Optional<T> findProjectedById(Long id, Class<T> type);
    Page<ProductProjection> findAllBy(Pageable pageable); //phân trang

    //    Optional<ProductProjection> findById(Long id);
    Optional<ProductProjection> findProjectionById(Long id);

    boolean existsByName(String name);
}
