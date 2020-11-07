package com.finbertmds.microservice.storage;

import com.finbertmds.microservice.storage.controllers.storage.StorageProperties;
import com.finbertmds.microservice.storage.controllers.storage.StorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan
@EnableAutoConfiguration
@EnableDiscoveryClient
@Component
@EnableConfigurationProperties(StorageProperties.class)
public class StorageApp {

	public static void main(String[] args) {
		SpringApplication.run(StorageApp.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
