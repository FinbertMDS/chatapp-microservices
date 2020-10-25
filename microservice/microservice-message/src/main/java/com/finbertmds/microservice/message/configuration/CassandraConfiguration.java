package com.finbertmds.microservice.message.configuration;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

@Configuration
// @EnableReactiveCassandraRepositories
public class CassandraConfiguration extends AbstractCassandraConfiguration{

    // TODO: update
    
    private static final Logger logger = LoggerFactory.getLogger(CassandraConfiguration.class);

    // private static final String CLUSTER_NAME = "db_messages";

    // @Inject
    // private Environment env;

    // @Profile("prod")
    // @Bean(destroyMethod = "shutDown")
    // public ManagerFactory cassandraNativeClusterProduction() {

    //     Cluster cluster = Cluster.builder().withoutJMXReporting()
    //             .addContactPoints(env.getProperty("spring.data.cassandra.contact-points"))
    //             .withPort(Integer.parseInt(env.getProperty("spring.data.cassandra.port"))).withClusterName(CLUSTER_NAME)
    //             .build();

    //     final ManagerFactory factory = ManagerFactoryBuilder.builder(cluster).build();
    //     final Session session = cluster.connect();

    //     maybeCreateSchema(session);
    //     return factory;
    // }

    // private void maybeCreateSchema(Session session) {
    //     logger.info("Execute schema creation script 'cassandra/schema_creation.cql' if necessary");
    //     final ScriptExecutor scriptExecutor = new ScriptExecutor(session);
    //     scriptExecutor.executeScript("cassandra/schema_creation.cql");

    // }



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

  // @Bean
  // public CqlSessionFactoryBean getCqlSession() {
  //   CqlSessionFactoryBean factory = new CqlSessionFactoryBean();
  //   factory.setUsername(userName);
  //   factory.setPassword(password);
  //   factory.setPort(port);
  //   factory.setKeyspaceName(keyspaceName);
  //   factory.setContactPoints(hostList);
  //   factory.setLocalDatacenter(datacenter);
  //   return factory;
  // }

  @Override
  protected String getKeyspaceName() {
    logger.info("keyspaceName: " + keyspaceName);
    return keyspaceName;
  }

  // @Override
  // protected String getLocalDataCenter() {
  //   logger.info("datacenter: " + datacenter);
  //   return datacenter;
  // }

  @Override
  protected String getContactPoints() {
    logger.info("hostList: " + hostList);
    return hostList;
  }

  @Override
  protected int getPort() {
    logger.info("port: " + port);
    return port;
  }
  
  @Override
  protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
      CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
              .createKeyspace(keyspaceName).ifNotExists()
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
