package com.finbertmds.microservice.contact.services;

import java.util.Optional;

import com.finbertmds.microservice.contact.models.Contact;
import com.finbertmds.microservice.contact.repository.ContactRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

  @Autowired
  private ContactRepository contactRepository;

  public Page<Contact> findAll(Pageable pageable) {
    return contactRepository.findAll(pageable);
  }

  public Boolean existsByEmail(String email) {
    return contactRepository.existsByEmail(email);
  }
  
  public Boolean existsByUsername(String username) {
    return contactRepository.existsByUsername(username);
  }

  public void save(Contact contact) {
    contactRepository.save(contact);
  }

  public Optional<Contact> findByEmail(String email) {
    return contactRepository.findByEmail(email);
  }

  public Optional<Contact> findByUsername(String username) {
    return contactRepository.findByUsername(username);
  }

  public Page<Contact> search(String query, Pageable pageable) {
    return contactRepository.findByUsernameContainingOrEmail(query, query, pageable);
  }
}
