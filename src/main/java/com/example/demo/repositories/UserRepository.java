package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByEmail(String email);
//    User findByUsernameOrEmail(String username, String email);
//    List<User> findByIdIn(List<Long> userIds);
//    User findByUsername(String username);
//    Boolean existsByUsername(String username);
//    Boolean existsByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

}
