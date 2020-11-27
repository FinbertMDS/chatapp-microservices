package com.finbertmds.microservice.contact.services;

import java.util.Optional;

import com.finbertmds.microservice.contact.models.ContactUser;
import com.finbertmds.microservice.contact.repository.ContactUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactUserService {

  @Autowired
  private ContactUserRepository contactUserRepository;
  
  public Optional<ContactUser> findById(Long id) {
    return contactUserRepository.findById(id);
  }
  
  public Optional<ContactUser> findByUsername(String username) {
    return contactUserRepository.findByUsername(username);
  }
  
  public ContactUser save(ContactUser user) {
    return contactUserRepository.save(user);
  }
}
