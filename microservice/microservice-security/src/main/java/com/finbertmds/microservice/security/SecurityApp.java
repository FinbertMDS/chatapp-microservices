package com.finbertmds.microservice.security;

import com.finbertmds.microservice.security.models.ERole;
import com.finbertmds.microservice.security.models.Role;
import com.finbertmds.microservice.security.repository.RoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient
@Component
public class SecurityApp extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SecurityApp.class, args);
	}

	@Bean
	public CommandLineRunner loadData(RoleRepository roleRepository) {
		return (args) -> {
			if (!roleRepository.findByName(ERole.ROLE_USER).isPresent()) {
				roleRepository.save(new Role(ERole.ROLE_USER));
			}
			if (!roleRepository.findByName(ERole.ROLE_MODERATOR).isPresent()) {
				roleRepository.save(new Role(ERole.ROLE_MODERATOR));
			}
			if (!roleRepository.findByName(ERole.ROLE_ADMIN).isPresent()) {
				roleRepository.save(new Role(ERole.ROLE_ADMIN));
			}
		};
	}

}
