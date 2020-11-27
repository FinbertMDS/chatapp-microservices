package com.finbertmds.microservice.contact.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.finbertmds.microservice.contact.security.repository", entityManagerFactoryRef = "securityEntityManager", transactionManagerRef = "securityTransactionManager")
public class PersistenceSecurityAutoConfiguration {
  @Autowired
  Environment env;

  @Bean
  public DataSource securityDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(env.getProperty("spring.jpa.properties.hibernate.dialect"));
    dataSource.setUrl(env.getProperty("finbertmds.security.datasource.url"));
    dataSource.setUsername(env.getProperty("finbertmds.security.datasource.username"));
    dataSource.setPassword(env.getProperty("finbertmds.security.datasource.password"));
    return dataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean securityEntityManager() {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(securityDataSource());
    em.setPackagesToScan("com.finbertmds.microservice.contact.security.model");

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    final HashMap<String, Object> properties = new HashMap<String, Object>();
    properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
    properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  public PlatformTransactionManager securityTransactionManager() {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(securityEntityManager().getObject());
    return transactionManager;
  }
}