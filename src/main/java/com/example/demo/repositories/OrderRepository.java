package com.example.demo.repositories;

import com.example.demo.models.Order;
import com.example.demo.projections.OrderProjection;
import com.example.demo.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    Optional<OrderProjection> findProjectionById(Long id);
    Page<OrderProjection> findAllBy(Pageable pageable); //phân trang

}
