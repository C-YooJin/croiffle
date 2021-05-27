package com.cfww.croiffle;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // 정적 자원에 대해서는 security 지원하지 않음
    @Override
    public void configure(WebSecurity web){
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // form기반의 로그인을 활용하는 경우 로그인 url, 로그인 성공시 url, 로그인 실패 url등을 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        // /about 요청에 대해서는 로그인을 요구함
        .antMatchers("/about").authenticated()
        // /admin 요청에 대해서는 ROLE_ADMIN 역할을 가지고 있어야 함
        .antMatchers("/admin").hasRole("ADMIN")
        // 나머지 요청에 대해서는 로그인을 요구하지 않음
        .anyRequest().permitAll()
        .and()
        // 로그인하는 경우에 대해 설정함
        .formLogin()
        // 로그인 페이지를 제공하는 URL을 설정함
        .loginPage("/user/loginView")
        // 로그인 성공 URL을 설정함
        .successForwardUrl("/index")
        // 로그인 실패 URL을 설정함
        .failureForwardUrl("/index")
        .permitAll()
        .and()
        .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/user/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        return new CustomLoginSuccessHandler();
    }
}
