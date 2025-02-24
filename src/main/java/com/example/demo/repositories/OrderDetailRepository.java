package com.example.demo.repositories;

import com.example.demo.models.Order;
import com.example.demo.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
//    List<OrderDetail> findByOrder(Order orderId); // Nếu OrderDetail có quan hệ @ManyToOne với Order
//List<OrderDetail> getOrderDetailByOrderId(Long orderId); //  Nếu OrderDetail chỉ có một trường orderId (Không dùng @ManyToOne)
    List<OrderDetail> findByOrderId(Long orderId);
}
