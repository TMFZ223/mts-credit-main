package com.example.creditservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final RestAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/loan-service/addTariff").hasRole("ADMIN")
                        .requestMatchers("/loan-service/deleteTariff").hasRole("ADMIN")
                        .requestMatchers("/loan-service/getOrdersByUserId").hasRole("ADMIN")
                        .requestMatchers("/loan-service/order").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/loan-service/deleteOrder").hasRole("ADMIN")
                        .requestMatchers("/loan-service/getStatusOrder").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/loan-service/getTariffs").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }
}
