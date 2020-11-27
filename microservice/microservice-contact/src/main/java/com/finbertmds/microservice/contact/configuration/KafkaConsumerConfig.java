package com.finbertmds.microservice.contact.configuration;

import java.util.HashMap;
import java.util.Map;

import com.finbertmds.microservice.contact.models.ContactUser;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

	@Value("${spring.kafka.consumer.group-id}")
  private String groupId;
  
  public ConsumerFactory<String, ContactUser> userConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

    return new DefaultKafkaConsumerFactory<>(
      props,
      new StringDeserializer(),
      new JsonDeserializer<>(ContactUser.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, ContactUser> userKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, ContactUser> factory =
      new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(userConsumerFactory());
    return factory;
  }
}
