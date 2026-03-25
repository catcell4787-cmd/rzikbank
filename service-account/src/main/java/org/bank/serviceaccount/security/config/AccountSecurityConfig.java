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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class AccountSecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(config ->
                        config
                            .loginPage("/auth/hello")
                            .defaultSuccessUrl("/auth/hello", true))
                            .logout(logout -> logout.logoutUrl("/logout"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/auth/login", "/auth/hello").permitAll()
                        .requestMatchers("/auth/update").hasAuthority("ADMIN")
                        .requestMatchers("/auth/{email}").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/clients/{email}/cards/registerCard", "/clients/{email}/cards/getCards").hasAnyAuthority("CLIENT", "MANAGER")
                        .requestMatchers("/clients/add").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/clients/{email}/loans/createLoan", "/clients/{email}/loans/getLoans").hasAnyAuthority("CLIENT", "MANAGER")
                        .requestMatchers("/clients/{email}", "/clients/{email}/updateStatus").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers("/managers/**").hasAuthority("ADMIN")
                        .requestMatchers("/managers/{email}").hasAnyAuthority("ADMIN", "MANAGER")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
