package com.cfww.croiffle.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getParameter("userEmail"), request.getParameter("userPw"));
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}

// 이런식으로 filter를 추가할 수 있음.
//@Log4j2
//public class TestAuthenticationFilter extends RequestCacheAwareFilter {
//    public TestAuthenticationFilter(AuthenticationManager authenticationManager) {
////        super.set
//}
//}
