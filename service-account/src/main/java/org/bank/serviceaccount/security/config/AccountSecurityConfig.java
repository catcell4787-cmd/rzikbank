package org.bank.serviceaccount.security.config;

import lombok.RequiredArgsConstructor;
import org.bank.serviceaccount.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class AccountSecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .cors(c -> c.configurationSource(
                        request -> {
                            var cors = new CorsConfiguration();
                            cors.setAllowedOrigins(List.of("http://localhost:3000")); // адрес вашего React-приложения
                            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            cors.setAllowedHeaders(List.of("*"));
                            cors.setAllowCredentials(true);
                            return cors;
                        }
                ))
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/auth/update").hasAuthority("ADMIN")
                        .requestMatchers("/auth/{email}").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/clients/{email}").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/clients/{email}/cards/register", "/clients/{email}/cards/get").hasAnyAuthority("CLIENT", "MANAGER", "ADMIN")
                        .requestMatchers("/clients/add").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/clients/{email}/loans/createLoan", "/clients/{email}/loans/getLoans").hasAnyAuthority("CLIENT", "MANAGER")
                        .requestMatchers("/clients/{email}", "/clients/{email}/updateStatus").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/managers/**").hasAuthority("ADMIN")
                        .requestMatchers("/managers/{email}").hasAnyAuthority("ADMIN", "MANAGER")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
