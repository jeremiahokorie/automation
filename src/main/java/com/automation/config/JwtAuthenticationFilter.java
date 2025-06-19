package com.automation.config;


import com.automation.util.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    // Add this debug logging to your JwtAuthenticationFilter
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        try {
//            String authHeader = request.getHeader("Authorization");
//            log.debug("Authorization Header: {}", authHeader); // Debug 1
//
//            String username = null;
//
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                String jwt = authHeader.substring(7);
//                log.debug("JWT Token: {}", jwt); // Debug 2
//                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//                if (jwtUtil.validateToken(jwt, userDetails)) {
//                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.getAuthorities()
//                    );
//                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(token);
//                }
////                if (jwtUtil.validateToken(jwt)) {
////                    String username = jwtUtil.extractUsername(jwt);
////                    List<String> roles = jwtUtil.extractRoles(jwt);
////                    log.debug("User: {}, Roles: {}", username, roles); // Debug 3
////
////                    UsernamePasswordAuthenticationToken authentication =
////                            new UsernamePasswordAuthenticationToken(
////                                    username,
////                                    null,
////                                    roles.stream()
////                                            .map(role -> new SimpleGrantedAuthority(role))
////                                            .collect(Collectors.toList())
////                            );
////                    SecurityContextHolder.getContext().setAuthentication(authentication);
////                } else {
////                    logger.error("JWT Validation Failed");
////                }
//            }
//        } catch (Exception e) {
//            log.error("Authentication Error", e);
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid authentication");
//            return;
//        }
//        chain.doFilter(request, response);
//    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        final String authorizationHeader = request.getHeader("Authorization");
//
//        try {
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                String jwt = authorizationHeader.substring(7);
//
//                // 1. Extract username and roles directly from token
//                String username = jwtUtil.extractUsername(jwt);
//                List<String> roles = jwtUtil.extractRoles(jwt); // Add this method to your JwtUtil
//
//                // 2. Create authorities with ROLE_ prefix
//                Collection<? extends GrantedAuthority> authorities = roles.stream()
//                        .map(role -> "ROLE_" + role) // Ensure prefix exists
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
//
//                // 3. Create authentication token with extracted authorities
//                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(
//                                    username,
//                                    null,
//                                    authorities
//                            );
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        } catch (ExpiredJwtException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Token expired. Please log in again");
//            return;
//        } catch (Exception e) {
//            logger.error("Authentication error", e);
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Invalid authentication");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        try {
            String username = null;
            String jwt = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                  jwt = authorizationHeader.substring(7).trim();
              //  jwt = authorizationHeader.split(" ")[1].trim();
                username = jwtUtil.extractUsername(jwt);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt.trim(), userDetails)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }catch (ExpiredJwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired. Please log in and try again");
            return;
        } catch (Exception e) {
            logger.error("Authentication error", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid authentication");
            return;
        }


        chain.doFilter(request, response);
    }




}
