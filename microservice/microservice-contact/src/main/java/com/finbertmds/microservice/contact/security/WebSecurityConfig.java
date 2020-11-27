package com.finbertmds.microservice.contact.security;

import java.util.Arrays;

import com.finbertmds.microservice.contact.security.jwt.AuthEntryPointJwt;
import com.finbertmds.microservice.contact.security.jwt.AuthTokenFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Configuration
	@Order(1)
	public static class RestApiSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private UserDetailsService userDetailsService;

		@Override
		public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
			authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Autowired
		private AuthEntryPointJwt unauthorizedHandler;

		@Bean
		public AuthTokenFilter authenticationJwtTokenFilter() {
			return new AuthTokenFilter();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.cors().and()
				.csrf().disable()
				.antMatcher("/api/**")
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
					.antMatchers("/api/**").hasAnyRole("USER")
					.anyRequest().authenticated();
			// @formatter:on
			http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		}

		@Bean
		CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
			configuration.addAllowedHeader("*");
			configuration.addAllowedOrigin("*");
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", configuration);
			return source;
		}
	}

	@Configuration
	@Order(2)
	public static class WebMvcSecurityConfig extends WebSecurityConfigurerAdapter {

		private static final String[] AUTH_WHITELIST = {
			// @formatter:off
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/swagger-ui.html",
			"/v2/api-docs/**",
			"/v3/api-docs/**"
			// @formatter:on	
		};

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers(AUTH_WHITELIST);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				// .cors().and()
				.csrf().disable()
				.authorizeRequests()
					.antMatchers("/actuator/**").permitAll()
					.anyRequest().authenticated();
			// @formatter:on
		}
	}
}
