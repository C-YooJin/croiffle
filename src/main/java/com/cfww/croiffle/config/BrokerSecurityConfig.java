package com.cfww.croiffle.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(2)
public class BrokerSecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class);

        http.antMatcher("/brokerr/**")
                .authorizeRequests().anyRequest().hasRole("BROKER")
                .and().formLogin();
    }
}
