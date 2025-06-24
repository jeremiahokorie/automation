package com.automation.util.jwt;

import com.automation.core.global.model.Roles;
import com.automation.core.global.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.security.KeyRep.Type.SECRET;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expiration;

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final SecretKey accessSecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode("U3ByaW5nU2VjdXJpdHlKV1RTZWNyZXRLZXlBdXRoZW50aWNhdGlvbg"));
    private final SecretKey refreshSecretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode("U3ByaW5nU2VjdXJpdHlKV1RTZWNyZXRLZXlBdXRoZW50aWNhdGlvbg"));
    private final long REFRESH_EXPIRATION = 1000 * 60 * 60 * 24 * 7;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        byte[] decodedSecret = Base64.getUrlDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedSecret);
        this.expiration = expiration;
    }

    private String createToken(String username, long expiration, SecretKey secret) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }



    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }



    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getEmail());
        claims.put("email", userDetails.getEmail());
        claims.put("firstName", userDetails.getFirstName());
        claims.put("lastName", userDetails.getLastName());
        List<String> roles = userDetails.getRoles().stream()
                .map(Roles::getValue)
                .collect(Collectors.toList());

//        List<String> roles = userDetails.getRoles().stream()
//                .map(role -> "ROLE_" + role.getValue())
//                .collect(Collectors.toList());
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, boolean isRefresh) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(isRefresh ? refreshSecretKey : accessSecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    // Extract specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // Make sure you're using the correct secret
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            Date expiration = claims.getExpiration();

            return (username.equals(userDetails.getUsername()) && !expiration.before(new Date()));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public String generateAccessToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername(),REFRESH_EXPIRATION, refreshSecretKey );
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername(), REFRESH_EXPIRATION, refreshSecretKey);
    }


    // Extract ALL claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Get signing key
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String generatePasswordResetToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject("resetToken")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
