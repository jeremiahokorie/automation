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
public class WebSecurityConfig{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationProvider customUserDetailService;
    // Inject your JwtAuthenticationFilter if you have one
    private final JwtAuthenticationFilter jwtAuthFilter;
//
//    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
//        this.jwtAuthFilter = jwtAuthFilter;
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/v3/api-docs/**",
//                                "/v3/api-docs",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html",
//                                "/swagger-resources/**",
//                                "/webjars/**"
//                        ).permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(customUserDetailService)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // 1. Disable CSRF (Essential if you are testing POST/PUT APIs via Swagger)
//                .csrf(csrf -> csrf.disable())
//
//                // 2. Configure endpoint permissions
//                .authorizeHttpRequests(auth -> auth
//                        // Allow public access to all Swagger UI files and assets
//                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/webjars/**").permitAll()
//                        // Allow public access to the OpenAPI JSON document endpoints
//                        .requestMatchers("/v3/api-docs/**", "/v3/api-docs.yaml").permitAll()
//                        // Any other API endpoints must be authenticated
//                        .anyRequest().authenticated()
//                )
//
//                // 3. Add your custom JWT filter BEFORE the standard security filter
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
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
                                "/swagger-ui/index.html",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/api/auth/register",
                                "/api/business/**",
                                "/api/environment/**",
                                "/api/certificates/occupancy/**"

                              //  process-automation-db-do-user-19197166-0.g.db.ondigitalocean.com
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("https://bauchi-mda.netlify.app", "http://localhost:9001", "http://localhost:8081"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
