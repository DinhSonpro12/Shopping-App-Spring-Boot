package com.example.demo.services;

import com.example.demo.dtos.UserDTO;
import com.example.demo.dtos.UserLoginDTO;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder; // không cần phải inject securityConfig vì sau khi tạo trong securityConfig thì nó sẽ tự inject vào PasswordEncoder

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String login(UserLoginDTO userLoginDTO) {
        User exitsUser = userRepository.findByPhoneNumber(userLoginDTO.getPhoneNumber());
        if (exitsUser == null) {
            throw new RuntimeException("User not found");
        }
//        không phải đăng nhập facebook thì mới check password
        if (userLoginDTO.getFacebookAccountId() == 0 && !passwordEncoder.matches(userLoginDTO.getPassword(), exitsUser.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

//        authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userLoginDTO.getPhoneNumber(),
                userLoginDTO.getPassword()
        );

        authenticationManager.authenticate(authenticationToken);

        return jwtUtil.generateToken(exitsUser);
    }

    @Override
    public User createUser(UserDTO userDto) throws Exception {
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new RuntimeException("Phone number already exists");
        }

        roleRepository.findById(userDto.getRoleId()).orElseThrow(() -> new Exception("Role not found"));
        User newUser = mapToUser(userDto);
        newUser.setIsActivated(true);

//        neu user dang nhap binhf thuong khong thong qua facebook
        if(userDto.getFacebookAccountId() == 0) {
//            xu ly ma hoa password
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

//    convert userDTO to User entity
    private User mapToUser (UserDTO userDto) {
        User user = new User();
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(userDto.getPassword());
        user.setFullName(userDto.getFullName());
        user.setAddress(userDto.getAddress());
        user.setBirthdate(userDto.getDateOfBirth());
        user.setFacebookId(userDto.getFacebookAccountId());
        user.setRole(roleRepository.findById(userDto.getRoleId()).get());
        return user;
    }

}
