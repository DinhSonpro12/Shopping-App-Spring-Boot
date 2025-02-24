package com.example.demo.services;

import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserLoginDTO;
import com.example.demo.models.User;

public interface IUserService {
    String login (UserLoginDTO userLoginDto);

    User createUser(UserDTO userDto) throws Exception;
}
