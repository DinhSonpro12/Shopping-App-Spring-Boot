package com.example.demo.controllers;

import com.example.demo.dtos.OrderDTO;
import com.example.demo.models.Order;
import com.example.demo.projections.AllOrderResponse;
import com.example.demo.projections.AllProductResponse;
import com.example.demo.projections.OrderProjection;
import com.example.demo.projections.ProductProjection;
import com.example.demo.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")

public class OrdersController {
    @Autowired
    private OrderService orderService;

    @GetMapping("user/{user_id}")
    public ResponseEntity<?> getOrders(
            @Valid @PathVariable ("user_id") Long userId
    ) {
        try {
            return ResponseEntity.ok(orderService.getOrdersByUserId(userId));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(
            @PathVariable("id") Long OrderID
    ) {
        try {
            OrderProjection orderProjection = orderService.getOrderById(OrderID);
            return ResponseEntity.ok(orderProjection);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("NOT FOUNR ORDER WITH ID = " + OrderID);
        }
    }


    @PostMapping("")
    public ResponseEntity<?> postOrder(
            @RequestBody OrderDTO orderDTO
    ) {
        Order newOrder = orderService.createOrder(orderDTO);
        return ResponseEntity.ok(newOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putOrder(
            @PathVariable("id") Long OrderID,
            @RequestBody OrderDTO orderDTO
    ) {
        try {

            return ResponseEntity.ok("put ok");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("NOT FOUNR ORDER WITH ID = " + OrderID);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(
            @PathVariable("id") Long OrderID
    ) {
        try {
            orderService.deleteOrder(OrderID);
            return ResponseEntity.ok("delete order with id = " + OrderID + " successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("NOT FOUNR ORDER WITH ID = " + OrderID);
        }
    }


}
