package com.finbertmds.microservice.message.configuration;

import javax.inject.Inject;

import com.finbertmds.microservice.message.security.Http401UnauthorizedEntryPoint;
import com.finbertmds.microservice.message.security.handlers.AjaxAuthenticationFailureHandler;
import com.finbertmds.microservice.message.security.handlers.AjaxAuthenticationSuccessHandler;
import com.finbertmds.microservice.message.security.handlers.AjaxLogoutSuccessHandler;
import com.finbertmds.microservice.message.security.service.CustomPersistentRememberMeService;
import com.finbertmds.microservice.message.security.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${chatapp.security.rememberme.key}")
    private String remembermeKey;

    @Inject
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Inject
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Inject
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private CustomUserDetailsService userDetailsService;

    @Inject
    private CustomPersistentRememberMeService rememberMeService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // @formatter:off
        web.ignoring()
            .antMatchers("/bower_components/**")
            .antMatchers("/fonts/**")
            .antMatchers("/images/**")
            .antMatchers("/views/login**")
            .antMatchers("/scripts/**")
            .antMatchers("/styles/**");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
        .and()
            .rememberMe()
            .rememberMeServices(rememberMeService)
            .key(remembermeKey)
        .and()
            .formLogin()
            .loginProcessingUrl("/authenticate")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .successHandler(ajaxAuthenticationSuccessHandler)
                .failureHandler(ajaxAuthenticationFailureHandler)
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler)
            .deleteCookies("JSESSIONID")
            .permitAll()
        .and()
            .csrf()
            .disable()
            .headers()
            .xssProtection().disable().and()
            .authorizeRequests()
                .antMatchers("/users").permitAll()
                .antMatchers("/security/remember-me").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/chat/**").authenticated()
                .antMatchers("/views/chat**").authenticated()
                .antMatchers("/users/**").authenticated()
                .antMatchers("/rooms/**").authenticated()
                .antMatchers("/rooms").authenticated()
                .antMatchers("/messages/**").authenticated();
        // @formatter:on
    }
}
