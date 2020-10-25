package com.finbertmds.microservice.message.security.authority;

import java.util.Collection;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.finbertmds.microservice.message.entity.UserAuthority;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="class")
public class CustomUserDetails implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = -9017041976056781934L;
    private Collection<? extends GrantedAuthority> authorities;
    private String login;
    private String password;

    public CustomUserDetails() {
    }

    public CustomUserDetails(Collection<? extends GrantedAuthority> authorities, String login, String password) {
        this.authorities = authorities;
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Set<UserAuthority> getUserAuthorities() {
        return (Set<UserAuthority>)authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
