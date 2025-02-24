package com.example.demo.filters;

import com.example.demo.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtTokenProvider;

    @Value("${api.v1.prefix}")
    String apiV1Prefix;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Lấy token từ header
        String header = request.getHeader("Authorization");

        // Nếu không có token, chuyển request đến filter tiếp theo
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7); // Bỏ qua "Bearer "



        if (isByPassToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // Nếu token hợp lệ, lấy thông tin xác thực từ token
//                Authentication authentication = jwtTokenProvider.getAuthentication(token, request);
                if(SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = jwtTokenProvider.getAuthentication(token, request);

                    // Đặt thông tin xác thực vào SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            // Xử lý lỗi nếu có lỗi trong quá trình xác thực
            SecurityContextHolder.clearContext(); // Làm sạch SecurityContext

            // Gửi lỗi phản hồi cho client nếu cần (tuỳ chỉnh)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Có thể thay đổi trạng thái HTTP tùy theo yêu cầu
            response.getWriter().write("Invalid or expired token"); // Gửi thông báo lỗi


//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            return; // Dừng chuỗi filter nếu có lỗi
        }

        filterChain.doFilter(request, response);
    }

    private boolean isByPassToken(HttpServletRequest request) {
        final Map<String, String> allowedPaths = Map.of(
                apiV1Prefix + "/users/register", "POST",
                apiV1Prefix + "/users/login", "POST",
                apiV1Prefix + "/users/refresh-token", "POST"
        );

        String path = request.getServletPath();

        for (Map.Entry<String, String> entry : allowedPaths.entrySet()) {
            if (path.contains(entry.getKey()) && request.getMethod().equals(entry.getValue())) {
                return true;
            }
        }

        return false;
    }

}
