package com.finbertmds.microservice.contact.services;

import java.util.Optional;

import com.finbertmds.microservice.contact.models.User;
import com.finbertmds.microservice.contact.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }
  
  public User save(User user) {
    return userRepository.save(user);
  }
}
