package com.automation.util.jwt;

import com.automation.core.global.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.security.KeyRep.Type.SECRET;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final long EXPIRATION_TIME = 86400000;
    private final String ACCESS_SECRET = "c2VjcmV0S2V5Rm9yTXlKd3RUb2tlbiEyMzQ1Ng++";
    private final String REFRESH_SECRET = "c2VjcmV0S2V5Rm9yTXlKd3RUb2tlbiEyMzQ1Ng--";
    private final long ACCESS_EXPIRATION = 1000 * 60 * 15; // 15 mins
    private final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days
// 1 day in ms

//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername());
//    }

//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }

    private String createToken(String username, long expiration, String secret) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

//    public String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token).getBody().getSubject();
//    }

//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }

//    public String generateToken(UserDetails userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .claim("roles", userDetails.getAuthorities().stream()
//                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
//                .compact();
//    }

    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("email", userDetails.getEmail());
        claims.put("firstName", userDetails.getFirstName());
        claims.put("lastName", userDetails.getLastName());
        claims.put("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
//        claims.put("roles", userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, boolean isRefresh) {
        try {
            Jwts.parser()
                    .setSigningKey(isRefresh ? REFRESH_SECRET : ACCESS_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }

    public String generateAccessToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername(),REFRESH_EXPIRATION, REFRESH_SECRET);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername(), REFRESH_EXPIRATION, REFRESH_SECRET);
    }


}
