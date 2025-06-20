package com.automation.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationProvider customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/swagger-resources/configuration/ui",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"

                        ).permitAll()
                        // User management
//                        .requestMatchers(HttpMethod.GET, "/api/admin/access/roles").hasRole("SUPERADMIN")
//                        .requestMatchers(HttpMethod.POST, "/api/admin/access/roles").hasRole("SUPERADMIN")
                        //.requestMatchers(HttpMethod.GET, "/api/dashboard/summary").hasRole("SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/auth/users").hasAnyRole("SUPERADMIN","SUPER_USER")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("SUPERADMIN")

                        // Environment registration api/environment/apply-permit
                        .requestMatchers(HttpMethod.POST, "/api/environment/apply").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/environment/apply").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/environment/approve/**").hasAnyRole("SUPERADMIN", "ADMIN")

                        // Business registration
                        .requestMatchers(HttpMethod.POST, "/api/business/register").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/business/businesses").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/business/approve/**").hasAnyRole("SUPERADMIN", "ADMIN", "USER","SUPER_USER")

                        // Certificate of occupancy
                        .requestMatchers(HttpMethod.POST, "/api/certificates/occupancy").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/certificates/occupancy/approve/**").hasAnyRole("SUPERADMIN", "ADMIN")

                        // System configuration
                        .requestMatchers("/api/system/**").hasRole("SUPERADMIN")

                        // All other authenticated requests
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(customUserDetailService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(auth -> auth
//                        // Public endpoints
//                        .requestMatchers(
//                                "/api/auth/**",
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/api/reports/public",
//                                "/v3/api-docs",
//                                "/v3/api-docs/**",
//                                "/swagger-resources",
//                                "/swagger-resources/**",
//                                "/swagger-resources/configuration/ui",
//                                "/configuration/ui",
//                                "/configuration/security",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html",
//                                "/v3/api-docs/**"
//                        ).permitAll()
//
//                        // User management
//                        .requestMatchers(HttpMethod.POST, "/api/users").hasRole("SUPERADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("SUPERADMIN")
//
//                        // Environment registration
//                        .requestMatchers(HttpMethod.POST, "/api/environment/apply").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/api/environment/approve/**").hasAnyRole("SUPERADMIN", "ADMIN")
//
//
//                        // Business registration
//                        .requestMatchers(HttpMethod.POST, "/api/business/register").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/api/business/approve/**").hasAnyRole("SUPERADMIN", "ADMIN", "USER","SUPER_USER")
//
//                        // Certificate of occupancy
//                        .requestMatchers(HttpMethod.POST, "/api/certificates/occupancy").authenticated()
//                        .requestMatchers(HttpMethod.PUT, "/api/certificates/occupancy/approve/**").hasAnyRole("SUPERADMIN", "ADMIN")
//
//                        // System configuration
//                        .requestMatchers("/api/system/**").hasRole("SUPERADMIN")
//
//                        // All other authenticated requests
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
////                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////                .authenticationProvider(customUserDetailService)
////                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://bauchi-mda.netlify.app"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
