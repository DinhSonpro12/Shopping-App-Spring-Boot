package com.example.demo.services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.dtos.OrderDTO;
import com.example.demo.models.Order;
import com.example.demo.projections.OrderProjection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {

    OrderProjection getOrderById(Long id) throws DataNotFoundException;

    List<Order> getOrdersByUserId(Long userId);

    Order createOrder(OrderDTO orderDto);

    Order updateOrder(OrderDTO orderDto, Long id) throws DataNotFoundException;

    void deleteOrder(Long id);
}
