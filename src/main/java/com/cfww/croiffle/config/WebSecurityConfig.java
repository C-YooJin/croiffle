package com.cfww.croiffle.config;

import com.cfww.croiffle.service.CustomAuthenticationFilter;
import com.cfww.croiffle.service.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * AuthenticationProvider는 UserDetails를 넘겨 받고 사용자 정보 비교
     * 인증이 완료 되면 사용자 정보를 담은 Authentication 객체를 반환
     */
    private AuthenticationProvider authenticationProvider;

    public WebSecurityConfig (AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    /** 스프링 시큐리티가 사용자를 인증하는 방법을 담은 구현체 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // AuthenticationProvider 구현체
        auth.authenticationProvider(authenticationProvider);
    }

    // 정적 자원에 대해서는 security 지원하지 않음
    @Override
    public void configure(WebSecurity web){
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // form기반의 로그인을 활용하는 경우 로그인 url, 로그인 성공시 url, 로그인 실패 url등을 설정
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class);

        /** 기본 인증 설정 */
        http.csrf().disable().authorizeRequests()
                // 아래 요청에 대해서는 모두가 접근할 수 있음
                .antMatchers("/", "/signin/**", "/signup/**").permitAll()
                // /about 요청에 대해서는 로그인을 요구함
                .antMatchers("/about").hasRole("BUYER")
                // /admin 요청에 대해서는 ROLE_ADMIN 역할을 가지고 있어야 함. Role.enum 파일과 매칭 돼야 함.
                .antMatchers("/broker").hasRole("BROKER")
                // 나머지 요청에 대해서는 로그인을 요구하지 않음. 일단 이렇게 해두고 추후에 수정 요함.
                // https://velog.io/@tmdgh0221/Spring-Security-%EC%99%80-OAuth-2.0-%EC%99%80-JWT-%EC%9D%98-%EC%BD%9C%EB%9D%BC%EB%B3%B4 에서
                // .anyRequest().authenticated() 이렇게 쓰면 나머지 리퀘스트에 대해서 로그인을 요한다는 뜻
                .anyRequest().permitAll()
                .and()
                // 로그인하는 경우에 대해 설정함
                .formLogin()
                // 로그인 페이지를 제공하는 URL을 설정함
                .loginPage("/user/loginView")
                // 로그인 성공 URL을 설정함 -> 브로커, 바이어 달라야 됨 (수정)
                .successForwardUrl("/index")
                // 로그인 실패 URL을 설정함
                .failureForwardUrl("/index")
                .permitAll()
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        /** 예외처리 */
        http.exceptionHandling()
                .authenticationEntryPoint(configAuthenticationEntryPoint())
                .accessDeniedHandler(configAccessDeniedHandler());


        /** OAuth 2.0 로그인 관련 처리 */
        http.oauth2Login()
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(configSuccessHandler())
                .failureHandler(configFailureHandler())
                .permitAll();

        http.httpBasic();
        http.logout()
                .deleteCookies("JSESSIONID");
    }

    /** SuccessHandler bean register */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler();
        successHandler.setDefaultTargetUrl("/index");
        return successHandler;
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


