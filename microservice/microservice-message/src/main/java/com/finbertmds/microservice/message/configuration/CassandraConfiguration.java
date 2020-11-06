package com.finbertmds.microservice.message.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {

  @Value("${spring.data.cassandra.contact-points}")
  private String hostList;

  @Value("${spring.data.cassandra.local-datacenter}")
  private String datacenter;

  @Value("${spring.data.cassandra.keyspace-name}")
  private String keyspaceName;

  @Value("${spring.data.cassandra.username}")
  private String userName;

  @Value("${spring.data.cassandra.password}")
  private String password;

  @Value("${spring.data.cassandra.port}")
  private Integer port;

  @Override
  protected String getKeyspaceName() {
    return keyspaceName;
  }

  @Override
  protected String getLocalDataCenter() {
    return datacenter;
  }

  @Override
  protected String getContactPoints() {
    return hostList;
  }

  @Override
  protected int getPort() {
    return port;
  }

  @Override
  protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
    CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspaceName).ifNotExists()
        .with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
    return Arrays.asList(specification);
  }

  @Override
  public SchemaAction getSchemaAction() {
    return SchemaAction.CREATE_IF_NOT_EXISTS;
  }

  @Override
  public String[] getEntityBasePackages() {
      return new String[] { "com.finbertmds.microservice.message.entity" };
  }
}
