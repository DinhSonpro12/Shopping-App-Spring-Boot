package com.example.demo.services;

import com.example.demo.dtos.OrderDetailDTO;
import com.example.demo.models.OrderDetail;
import com.example.demo.repositories.OrderDetailRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService{
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderDetail getOrderDetailById(Long id) throws Exception {
        return orderDetailRepository.findById(id).orElseThrow(() -> new Exception("Not found OrderDetail with id = " + id));
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException("User with id = " + orderId + " not found");
        }

        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        if(!orderRepository.existsById(orderDetailDTO.getOrderId())) {
            throw new EntityNotFoundException("Not found Order with id = " + orderDetailDTO.getOrderId());
        } else if (!productRepository.existsById(orderDetailDTO.getProductId())) {
            throw new Exception("Not found Product with id = " + orderDetailDTO.getProductId());
        }

        OrderDetail newOrderDetail = mapToEntity(orderDetailDTO);
        return orderDetailRepository.save(newOrderDetail);
    }

    @Override
    public OrderDetail updateOrderDetail(OrderDetailDTO orderDetailDTO, Long id) throws Exception{
        if (!orderDetailRepository.existsById(id)) {
            throw new Exception("Not found OrderDetail with id = " + id);
        } else if(!orderRepository.existsById(orderDetailDTO.getOrderId())) {
            throw new EntityNotFoundException("Not found Order with id = " + orderDetailDTO.getOrderId());
        } else if (!productRepository.existsById(orderDetailDTO.getProductId())) {
            throw new Exception("Not found Product with id = " + orderDetailDTO.getProductId());
        }

        OrderDetail orderDetail = mapToEntity(orderDetailDTO);
        orderDetail.setId(id);
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) throws Exception {
        if(!orderDetailRepository.existsById(id)) {
            throw new Exception ("Not found OrderDetail with id = " + id);
        }
        orderDetailRepository.deleteById(id);
    }


    private OrderDetail mapToEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(orderRepository.findById(orderDetailDTO.getOrderId()).get());
        orderDetail.setProduct(productRepository.findById(orderDetailDTO.getProductId()).get());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setColor(orderDetailDTO.getColor());
        orderDetail.setTotalMoney(orderDetailDTO.getTotal());

        return orderDetail;
    }
}
