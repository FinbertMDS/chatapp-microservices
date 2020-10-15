package com.finbertmds.microservice.filestorage;

import com.finbertmds.microservice.filestorage.controllers.storage.StorageProperties;
import com.finbertmds.microservice.filestorage.controllers.storage.StorageService;

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
public class FilestorageApp {

	public static void main(String[] args) {
		SpringApplication.run(FilestorageApp.class, args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
