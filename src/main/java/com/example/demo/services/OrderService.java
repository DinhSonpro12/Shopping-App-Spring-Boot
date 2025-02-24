package com.example.demo.services;

import com.example.demo.Exception.DataNotFoundException;
import com.example.demo.dtos.OrderDTO;
import com.example.demo.models.Order;
import com.example.demo.models.OrderStatus;
import com.example.demo.projections.OrderProjection;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OrderProjection getOrderById(Long orderId) throws DataNotFoundException {
        return orderRepository.findProjectionById(orderId).orElseThrow(() -> new DataNotFoundException("Order with id = " + orderId + " not found"));
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User with id = " + userId + " not found");
        }
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Order createOrder(OrderDTO orderDto) {
        Order order = mapToEntity(orderDto); // map từ DTO sang Entity

        order.setShippingDate(java.time.LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
//        tracking number
        order.setTrackingNumber(java.util.UUID.randomUUID().toString());
//        is active
        order.setIsActive(true);
//        payment status
        order.setPaymentStatus("PENDING");

        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(OrderDTO orderDto, Long id) throws DataNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new DataNotFoundException("Order with id = " + id + " not found");
        }

        if (userRepository.existsById(orderDto.getUserId())) {
            throw new DataNotFoundException("User with id = " + orderDto.getUserId() + " not found");
        }

        Order order = mapToEntity(orderDto); // map từ DTO sang Entity

        order.setShippingDate(java.time.LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
//        tracking number
        order.setTrackingNumber(java.util.UUID.randomUUID().toString());
//        is active
        order.setIsActive(true);
//        payment status
        order.setPaymentStatus("PENDING");

        order.setId(id);

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order exitsOrder = orderRepository.findById(id).get();

        if (!exitsOrder.getIsActive()) {
            throw new RuntimeException("Order with id = " + id + " is deleted");
        } else {
            exitsOrder.setIsActive(false);
            orderRepository.save(exitsOrder);
        }
    }


    private Order mapToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setAddress(orderDTO.getAddress());
        order.setFullName(orderDTO.getFullName());
        order.setEmail(orderDTO.getEmail());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setNote(orderDTO.getNote());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setShippingMethod(orderDTO.getShippingMethod());
        order.setUser(userRepository.findById(orderDTO.getUserId()).get());
        return order;
    }
}
