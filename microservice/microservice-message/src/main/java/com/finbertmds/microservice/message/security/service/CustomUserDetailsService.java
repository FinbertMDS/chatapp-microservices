package com.finbertmds.microservice.message.security.service;

import com.finbertmds.microservice.message.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Inject
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userService.findByLogin(username).toUserDetails();
    }
}
