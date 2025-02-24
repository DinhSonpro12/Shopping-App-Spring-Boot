package com.example.demo.projections;

import com.example.demo.models.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"user"}) // Định nghĩa thứ tự
public interface OrderProjection {
    Long getId();
    String getFullName();
    String getAddress();
    String getEmail();
    String getPhoneNumber();
    UserProjection getUser();
}



