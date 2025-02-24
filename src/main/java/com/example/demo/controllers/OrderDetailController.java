package com.example.demo.controllers;


import com.example.demo.dtos.OrderDetailDTO;
import com.example.demo.models.OrderDetail;
import com.example.demo.services.OrderDetailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1.prefix}/order_details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
//    Lấy detail mot san phan trong ORDER
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
            @PathVariable("id") Long OrderDetailID
    ) {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetailById(OrderDetailID);
            return ResponseEntity.ok(orderDetail);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

//    Lay tat ca detail cua mot ORDER
    @GetMapping("/order/{id_order}")
    public ResponseEntity<?> getOrderDetails(
            @PathVariable("id_order") Long OrderID
    ) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailsByOrderId(OrderID);
            return ResponseEntity.ok(orderDetails);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

//    Them mot detail vao ORDER
    @PostMapping("")
    public ResponseEntity<?> postOrderDetail(
            @Valid  @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult bindingResult
            ) {

        if(bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(400).body(errorMessages);
        }

//        khong co loi thi save detail vo order
        try {
            OrderDetail newOrderDetail =  orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(newOrderDetail);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

//    Sua mot detail trong ORDER
    @PutMapping("/{id}")
    public ResponseEntity<?> putOrderDetail(
            @PathVariable("id") Long OrderDetailID,
            @Valid @RequestBody OrderDetailDTO orderDetailDTO,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()){
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.status(400).body(errorMessages);
        }

        try {
            OrderDetail updateOrderDetail = orderDetailService.updateOrderDetail(orderDetailDTO, OrderDetailID);
            return ResponseEntity.ok(updateOrderDetail);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

//    Xoa mot detail trong ORDER
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(
            @PathVariable("id") Long OrderDetailId
    ) {
        try {
            orderDetailService.deleteOrderDetail(OrderDetailId);
            return ResponseEntity.ok("Delete" + OrderDetailId + "success");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
