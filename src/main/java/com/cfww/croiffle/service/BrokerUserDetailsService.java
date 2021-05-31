package com.cfww.croiffle.service;

import com.cfww.croiffle.domain.repository.BrokerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrokerUserDetailsService implements UserDetailsService {

    private final BrokerRepository brokerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) brokerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자르 찾을 수 없습니다."));
    }


}
