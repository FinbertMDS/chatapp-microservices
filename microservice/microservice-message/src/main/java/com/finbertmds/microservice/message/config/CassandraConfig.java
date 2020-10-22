package com.finbertmds.microservice.message.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@EnableReactiveCassandraRepositories
public class CassandraConfig {

  @Value("${spring.data.cassandra.contact-points}")
  private String hostList;

  @Value("${spring.data.cassandra.datacenter}")
  private String datacenter;

  @Value("${spring.data.cassandra.keyspace-name}")
  private String keyspaceName;

  @Value("${spring.data.cassandra.username}")
  private String userName;

  @Value("${spring.data.cassandra.password}")
  private String password;

  @Value("${spring.data.cassandra.port}")
  private Integer port;

  @Bean
  public CqlSessionFactoryBean getCqlSession() {
    CqlSessionFactoryBean factory = new CqlSessionFactoryBean();
    factory.setUsername(userName);
    factory.setPassword(password);
    factory.setPort(port);
    factory.setKeyspaceName(keyspaceName);
    factory.setContactPoints(hostList);
    factory.setLocalDatacenter(datacenter);
    return factory;
  }

}