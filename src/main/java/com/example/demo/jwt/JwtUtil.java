package com.example.demo.jwt;

import com.example.demo.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;  // Mã hóa khóa bí mật của bạn

    @Value("${jwt.expirationTime}")
    private long expirationTime;  // Thời gian hết hạn của token (1 giờ)

    @Autowired
    private UserDetailsService userDetailsService;

    // Tạo JWT từ thông tin user
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            return Jwts.builder()
//                .claim("username", username) // Claim là một cặp key-value chứa thông tin bổ sung trong token.
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber()) // Subject (chủ thể) của JWT, thường là một ID hoặc tên của người dùng.
                    .setIssuedAt(new Date()) // Thiết lập thời điểm phát hành token (Issued At - iat) là thời gian hiện tại.
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // khoảng thời gian token có hiệu lực, tính bằng milliseconds.
                    .signWith(SignatureAlgorithm.HS256, getSigningKey()) // Ký JWT bằng thuật toán HS256 (HMAC với SHA-256).  - secretKey là khóa bí mật dùng để ký token, giúp xác thực tính hợp lệ khi giải mã.
                    .compact(); //Chuyển JWT thành chuỗi String
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    // Lấy thông tin user từ JWT
//    extractAllClaims: Trích xuất tất cả các claim từ JWT
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    checkExpiration: Kiểm tra xem token có hết hạn không. - true - đã hết hạn
    private Boolean isTokenExpired(Date expiration) {
//        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }


    // Xác minh token
    public boolean validateToken(String token) {
        try {
            // Giải mã token và kiểm tra chữ ký
            Claims claims = extractAllClaims(token);

            return !isTokenExpired(claims.getExpiration()); // Trả về true nếu token chưa hết hạn

        } catch (Exception e) {
            // Nếu có lỗi (ví dụ: token không hợp lệ, đã hết hạn), trả về false
            return false;
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token, HttpServletRequest request) {
        Claims claims = extractAllClaims(token);

        // Lấy thông tin người dùng từ UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

        // Lấy danh sách quyền từ UserDetails
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        UsernamePasswordAuthenticationToken newAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails ,
                        null,
                        authorities
                );

//        để giúp lưu thêm thông tin chi tiết của request: IP, session ID, ...
        newAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return newAuthenticationToken;
    }

}
