package com.example.demo.configurations;


import com.example.demo.filters.JwtTokenFilter;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.stream.Collectors;

@Configuration
@EnableMethodSecurity

public class WebScurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenFilter jwtTokenFilter; // Filter để xác thực token

    @Value("${api.v1.prefix}")
    private String apiV1Prefix;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    // user's detail object
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("🔥 JwtTokenFilter is running for request: " + http); // Thêm log để kiểm tra filter có chạy không

        http
                .csrf(csrf -> csrf.disable()) // Tắt CSRF (nếu là API)
                .authorizeHttpRequests(auth -> auth
//                       ------------ ALL
                                .requestMatchers(HttpMethod.GET,
                                        apiV1Prefix + "/categories",
                                        apiV1Prefix + "/categories/*",
                                        apiV1Prefix + "/products",
                                        apiV1Prefix + "/products/*"
                                ).permitAll() // Cho phép truy cập không cần login
                                .requestMatchers(HttpMethod.POST,
                                        apiV1Prefix + "/users/login",
                                        apiV1Prefix + "/users/register"
                                ).permitAll()

//                     -------------- ADMIN
//                                .requestMatchers(HttpMethod.POST,
//                                        apiV1Prefix + "/products"
//                                ).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,
                                        apiV1Prefix + "/categories",
                                        apiV1Prefix + "/products",
                                        apiV1Prefix + "/products/upload/*"
                                ).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,
                                        apiV1Prefix + "/categories/*",
                                        apiV1Prefix + "/products/*"
                                ).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,
                                        apiV1Prefix + "/categories/*",
                                        apiV1Prefix + "/products/*"
                                ).hasRole("ADMIN")
//                      -------------- USER
                                .requestMatchers(HttpMethod.GET,
                                        apiV1Prefix + "/order_details/*",
                                        apiV1Prefix + "/order_details/order/*",
                                        apiV1Prefix + "/orders/*",
                                        apiV1Prefix + "/orders/user/*"
                                )
                                .hasRole("USER")
                                .requestMatchers(HttpMethod.POST,
                                        apiV1Prefix + "/order_details",
                                        apiV1Prefix + "/orders"
                                ).hasRole("USER")
                                .requestMatchers(HttpMethod.PUT,
                                        apiV1Prefix + "/order_details/*",
                                        apiV1Prefix + "/orders/*"
                                ).hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE,
                                        apiV1Prefix + "/order_details/*",
                                        apiV1Prefix + "/orders/*"
                                ).hasRole("USER")
                                .anyRequest().authenticated() // Các request khác yêu cầu login
                )
                .addFilterBefore((request, response, chain) -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null && authentication.isAuthenticated()) {
                        String username = authentication.getName();
                        String roles = authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(", "));
                        logger.info("📌 User: {} | Roles: {}", username, roles);
                    } else {
                        logger.info("📌 Chưa có user nào đăng nhập.");
                    }
                    chain.doFilter(request, response);
                }, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Thêm filter vào trước UsernamePasswordAuthenticationFilter

//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Không dùng session
//                .httpBasic(withDefaults()); // Sử dụng Basic Auth (username/password)
//                .formLogin(withDefaults()); // Sử dụng Form Login
        return http.build();
    }
}
