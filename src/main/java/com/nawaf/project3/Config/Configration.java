package com.nawaf.project3.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Configration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/customer/register").permitAll()

                        .requestMatchers("/api/v1/employee/**").hasRole("ADMIN")

                        .requestMatchers("/api/v1/user/**").hasRole("ADMIN")

                        .requestMatchers("/api/v1/customer/get-all", "/api/v1/customer/get-by-id/**")
                        .hasAnyRole("ADMIN", "EMPLOYEE")

                        .requestMatchers("/api/v1/customer/my-profile", "/api/v1/customer/update/**",
                                "/api/v1/customer/delete/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers("/api/v1/account/get-all", "/api/v1/account/get-by-id/**",
                                "/api/v1/account/update/**", "/api/v1/account/activate/**",
                                "/api/v1/account/block/**")
                        .hasAnyRole("ADMIN", "EMPLOYEE")

                        .requestMatchers("/api/v1/account/delete/**").hasRole("ADMIN")

                        .requestMatchers("/api/v1/account/details/**", "/api/v1/account/my-accounts",
                                "/api/v1/account/add", "/api/v1/account/deposit/**",
                                "/api/v1/account/withdraw/**", "/api/v1/account/transfer/**")
                        .hasRole("CUSTOMER")

                        .anyRequest().authenticated()
                )

                .httpBasic(basic -> {})

                .build();
    }
}