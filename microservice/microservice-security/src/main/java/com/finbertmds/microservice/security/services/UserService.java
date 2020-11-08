package com.finbertmds.microservice.security.services;

import com.finbertmds.microservice.security.models.User;
import com.finbertmds.microservice.security.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserRepository userRepository;

  private KafkaTemplate<String, User> kafkaTemplate;

  @Autowired
  public UserService(KafkaTemplate<String, User> kafkaTemplate) {
    super();
    this.kafkaTemplate = kafkaTemplate;
  }

  public void save(User user) {
		userRepository.save(user);
    fireUserCreatedEvent(user);
  }

	private void fireUserCreatedEvent(User user) {
		log.info("Send user " + user.getId());
		kafkaTemplate.send("user", user.getId() + "created", user);
	}

  public Boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public Boolean existsByEmail(String email) {
    return userRepository.existsByUsername(email);
  }
  
}
