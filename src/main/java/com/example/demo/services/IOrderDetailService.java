package com.example.demo.services;

import com.example.demo.dtos.OrderDetailDTO;
import com.example.demo.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail getOrderDetailById(Long id) throws Exception;
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail updateOrderDetail(OrderDetailDTO orderDetailDTO, Long id) throws Exception;
    void deleteOrderDetail(Long id) throws Exception;
}
