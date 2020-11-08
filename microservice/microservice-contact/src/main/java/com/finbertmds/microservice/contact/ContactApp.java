package com.finbertmds.microservice.contact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient
@Component
public class ContactApp {

	public static void main(String[] args) {
		SpringApplication.run(ContactApp.class, args);
	}

}
