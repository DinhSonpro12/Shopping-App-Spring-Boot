package com.example.demo.controllers;


import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserLoginDTO;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")

public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity.ok("get users ok");
//        return ResponseEntity.status(200).body("get users ok");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("get users failed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) {
                List<String> errorMessages = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return  ResponseEntity.badRequest().body(errorMessages);
            }
//            Xu ly logic tao user
//            check pass = retype pass
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.status(400).body("Password and retype password are not the same");
            }

            User newUser = userService.createUser(userDTO);

            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO,
                                   BindingResult bindingResult) {
        try {
//            check thông tin đăng nhaapj -> trả token cho user
            String token = userService.login(userLoginDTO);

            return ResponseEntity.ok("Login successfully " + token);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }



}
