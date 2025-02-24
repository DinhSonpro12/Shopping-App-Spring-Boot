package com.example.demo.repositories;

import com.example.demo.models.Category;
import com.example.demo.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
//@Component
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Category findByName(String name);
//    List<OrderDetail> findByOrderId (Long orderId);

}
