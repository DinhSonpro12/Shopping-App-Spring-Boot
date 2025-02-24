package com.example.demo.dtos;

//user_id INT not null,
//fullname VARCHAR(100) ,
//email VARCHAR(100) ,
//phone_number VARCHAR(10) NOT NULL,
//order_date datetime DEFAULT CURRENT_TIMESTAMP,
//address VARCHAR(100) NOT NULL,
//total_price FLOAT CHECK(total_price >= 0),
//status VARCHAR(100),
//shipping_method VARCHAR(100),
//shipping_address VARCHAR(100),
//shipping_date VARCHAR(100),
//tracking_number VARCHAR(100),
//payment_method VARCHAR(100)

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class OrderDTO {

    @NotBlank (message = "Fullname is mandatory")
    @JsonProperty("fullname")
    private String fullName;

    @NotBlank (message = "Email is mandatory")
    private String email;
    private String note;
    private String address;

    @JsonProperty("user_id")
    private Long userId;

    @Size(min = 9, max = 12)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("payment_method")
    private String paymentMethod;


    // Getter and Setter methods
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod (String payment_method) {
        this.paymentMethod = payment_method;
    }

}
