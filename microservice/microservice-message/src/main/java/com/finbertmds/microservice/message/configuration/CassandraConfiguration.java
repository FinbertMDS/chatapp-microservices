package com.finbertmds.microservice.message.configuration;

import java.util.Arrays;

import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;

@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "db_messages";
    }

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints("cassandra");
        cluster.setPort(9042);
        cluster.setKeyspaceCreations(
                Arrays.asList(new CreateKeyspaceSpecification("db_messages").ifNotExists().withSimpleReplication(1)));
        cluster.setStartupScripts(Arrays.asList("USE db_messages",
                "CREATE TABLE IF NOT EXISTS messages (" + "username text," + "chatRoomId text," + "date timestamp,"
                        + "fromUser text," + "toUser text," + "text text," + "isNotification boolean,"
                        + "PRIMARY KEY ((username, chatRoomId), date)" + ") WITH CLUSTERING ORDER BY (date ASC)"));
        return cluster;
    }

    @Bean
    @Override
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }
}
