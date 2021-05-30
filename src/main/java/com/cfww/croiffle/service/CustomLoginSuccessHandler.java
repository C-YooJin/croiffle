package com.cfww.croiffle.service;

import com.cfww.croiffle.domain.entity.Broker;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {

        final Broker broker = ((MyUserDetails)authentication.getPrincipal()).getUser();
        final String token = TokenUtils.getnerateJwtToken(broker);
        response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE+ " " + token);

    }
}
