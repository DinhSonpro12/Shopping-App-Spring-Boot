package com.example.demo.dtos;

//id INT AUTO_INCREMENT PRIMARY KEY,
//fullname VARCHAR(100) DEFAULT "",
//phone_number VARCHAR(10) NOT NULL,
//address VARCHAR(100) DEFAULT "",
//password VARCHAR(100) NOT NULL,-- password da ma hoa
//create_at datetime DEFAULT CURRENT_TIMESTAMP,
//update_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//is_active tinyint(1) DEFAULT 1,
//date_of_birth date DEFAULT NULL
//facebook_account_id INT DEFAULT 0,

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class UserLoginDTO {

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

//    @NotBlank(message = "Password is mandatory")
    private String password;

    private Long facebookAccountId;


    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getFacebookAccountId() {
        return facebookAccountId;
    }

    public void setFacebookAccountId(Long facebookAccountId) {
        this.facebookAccountId = facebookAccountId;
    }


}
